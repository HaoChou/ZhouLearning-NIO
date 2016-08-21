package Zhoulearning.NIO.Example;

import java.util.BitSet;

/**
 * Created by Zhou on 2016/8/22.
 */
public class UnicodeTest {
    public static void main(String[] args) {
//        java中的字符就是指char类型的变量，无论中文还是英文，都是占2个字节，因为都是用Unicode编码，一个Unicode编码就是16位，也就是2个字节。
        char a='a';
        char b='中';
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));

    }
}
