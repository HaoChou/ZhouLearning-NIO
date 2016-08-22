package Zhoulearning.NIO.Example.channelTransferExample;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class transferExample {

    public static void main(String[] args) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("testFiles/fromFile.txt", "r");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("testFiles/toFile.txt", "rw");//mode  must be one of "r", "rw", "rws", or "rwd"
        FileChannel toChannel = toFile.getChannel();



        long position=0;
        long count=fromChannel.size();

        toChannel.transferFrom(fromChannel,position,count);

//      toChannel.position(toChannel.size());//在后面追加
//      fromChannel.transferTo(position,count,toChannel);
        fromChannel.close();
        toChannel.close();
        toFile.close();
        fromFile.close();

    }
}
