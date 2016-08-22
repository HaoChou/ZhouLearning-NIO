package Zhoulearning.NIO.Example.ScatterGatherExample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class ScatteringReadExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("ScatterRead.txt", "r");
        /*
        *txt的内容 在idea中输入
        * 123456789
        *abcdefghijklmn
        * */

        FileChannel fileChannel = aFile.getChannel();

        ByteBuffer headerBuffer=ByteBuffer.allocate(11);
        ByteBuffer bodyBuffer=ByteBuffer.allocate(128);
        ByteBuffer[] byteBuffers={headerBuffer,bodyBuffer};

        fileChannel.read(byteBuffers);

        bodyBuffer.flip();
        while (bodyBuffer.hasRemaining()){
            System.out.println((char)bodyBuffer.get());
        }

        headerBuffer.flip();
        while (headerBuffer.hasRemaining()){
            System.out.println(headerBuffer.get());
        }
        /*
        *
        *
        * 在ascii 十是换行键 十三是回车键 在idea中的回车 包含了这两个字符  \r\n
        *
        * */

        aFile.close();
    }
}
