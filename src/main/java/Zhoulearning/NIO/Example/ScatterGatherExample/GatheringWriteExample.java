package Zhoulearning.NIO.Example.ScatterGatherExample;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class GatheringWriteExample {

    public static void main(String[] args) throws IOException {

        RandomAccessFile aFile = new RandomAccessFile("ScatterRead.txt", "r");
        FileChannel fileChannel = aFile.getChannel();
        ByteBuffer headerBuffer=ByteBuffer.allocate(11);
        ByteBuffer bodyBuffer=ByteBuffer.allocate(128);
        ByteBuffer[] byteBuffers={headerBuffer,bodyBuffer};//顺序
        fileChannel.read(byteBuffers);

        File outputFile = new File("testFiles/GatheringWrite.txt");

        //记得flip
        headerBuffer.flip();
        bodyBuffer.flip();
        ByteBuffer[] outByteBuffers={bodyBuffer,headerBuffer};//颠倒

        if(!outputFile.exists()) {
            outputFile.createNewFile();
        }
        //实例化输入流，并且获取相对应的FileChannel实例
        FileOutputStream fos = new FileOutputStream(outputFile);
        FileChannel outputChannel = fos.getChannel();

        outputChannel.write(outByteBuffers);
        fos.close();
        fileChannel.close();

    }
}
