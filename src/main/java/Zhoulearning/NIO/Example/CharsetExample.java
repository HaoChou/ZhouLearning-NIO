package Zhoulearning.NIO.Example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by Zhou on 2016/8/22.
 */
public class CharsetExample {
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");

        FileChannel inChannel = aFile.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        StringBuilder sb = new StringBuilder();

//        Charset charset = Charset.forName("GBK");
        Charset charset = Charset.forName("UTF-8");
        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            sb.append(charset.decode(byteBuffer));
            byteBuffer.clear();
        }

        System.out.println(sb.toString());
        aFile.close();

    }
}
