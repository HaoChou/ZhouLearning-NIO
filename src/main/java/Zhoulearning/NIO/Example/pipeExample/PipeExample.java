package Zhoulearning.NIO.Example.pipeExample;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by zhouhao9 on 2016/8/23.
 */
public class PipeExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();
        final Pipe.SourceChannel sourceChannel = pipe.source();

        new Thread() {
            private ByteBuffer byteBuffer;

            @Override
            public void run() {
                System.out.println("Reader is running!");
                byteBuffer = ByteBuffer.allocate(100);
                while (true) {

                    try {
                        int i = sourceChannel.read(byteBuffer);
                        if (i > 0) {
                            byteBuffer.flip();
                            System.out.println("message get:" + i + "byte:" + Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString());
                            byteBuffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        Thread.sleep(3000);
        String msg = "Time is " + new Date().toString();

        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
//        ByteBuffer byteBuffer=ByteBuffer.allocate(50);
//        byteBuffer.put(msg.getBytes("UTF-8"));
//        byteBuffer.flip();
        while (true) {
            Scanner scan = new Scanner(System.in);//键盘输入数据
            String string = scan.next();
            sinkChannel.write(ByteBuffer.wrap(string.getBytes("UTF-8")));
            System.out.println("message send!");
        }

    }
}
