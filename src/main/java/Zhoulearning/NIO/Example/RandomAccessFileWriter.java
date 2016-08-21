package Zhoulearning.NIO.Example;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Zhou on 2016/8/21.
 */
public class RandomAccessFileWriter {
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");

        aFile.writeChar('a');//因为writeChar方法写入的是char数据类型，也就是Unicode字符，Unicode就是用两个字节表示的 所以有两个字符 一个是空字符 另外一个是a
        aFile.writeChar('b');
        aFile.writeChar('c');
        //这样文件的内容不就应该是abc了吗?

        aFile.seek(2); //指针设置在a后.
        aFile.writeChar('d'); //文件理应变成adbc,但貌似不是这么回事,如下

        StringBuffer strBuf = new StringBuffer();
        aFile.seek(0); //指针归回初始位置
        strBuf.append(aFile.readChar());
        strBuf.append(aFile.readChar());
        strBuf.append(aFile.readChar());

        System.out.println(strBuf); //结果是adc
        int i=0;
        do{
            aFile.writeChar('a');
            aFile.writeChar('b');
            aFile.writeChar('c');
            aFile.writeChar('E');
            aFile.writeChar('f');
            aFile.writeChar('G');
            aFile.writeChar('\n');
            i++;
        }while (i<10);




    }
}
