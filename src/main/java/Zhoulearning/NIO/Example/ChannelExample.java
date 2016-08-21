package Zhoulearning.NIO.Example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by Zhou on 2016/8/21.
 */
public class ChannelExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");


        FileChannel inChannel=aFile.getChannel();

        ByteBuffer byteBuffer= ByteBuffer.allocate(8);
//        Charset charset = Charset. forName("UTF-8") ;
//        CharBuffer charBuffer = charset.decode(byteBuffer);
//        byteBuffer=charset.encode(charBuffer);

        int byteRead=inChannel.read(byteBuffer);
int x=0;
        while (byteRead != -1){

//            System.out.println("Read"+byteRead);
            byteBuffer.flip();//   limit = position;position = 0; mark = -1;

            while (byteBuffer.hasRemaining()){
//                x++;
//                System.out.print((char)byteBuffer.get()+""+x);
//                System.out.println(byteBuffer.get());  //可见输出中有0 在ascii表中 0代表null 空字符
                System.out.print((char)byteBuffer.get());
            }
            byteBuffer.clear();
            byteRead=inChannel.read(byteBuffer);
        }
        aFile.close();

    }
}
