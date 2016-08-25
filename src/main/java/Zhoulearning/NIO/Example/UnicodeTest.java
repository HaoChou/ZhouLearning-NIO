package Zhoulearning.NIO.Example;

import java.util.BitSet;

/**
 * Created by Zhou on 2016/8/22.
 */
public class UnicodeTest {
    public static void main(String[] args) {
//        java中的字符就是指char类型的变量，无论中文还是英文，都是占2个字节，因为都是用Unicode编码，一个Unicode编码就是16位，也就是2个字节。
        char[] cas={'a'};
        char[] cbs={'中'};
        System.out.println(String.copyValueOf(cbs).getBytes().length);//3个字节 utf-8 中文三个字节
        System.out.println(String.copyValueOf(cas).getBytes().length);//1个字节

//
        byte b=127;
        System.out.println(byteToIntTest(b));
        int a= (int) Math.pow(2, 34);//int强制转换 丢失精度 等于2^31次方
        System.out.println("a:"+a);

        //两个结果不同 int在2的31次方时候
        System.out.println((int) Math.pow(2,30)*2-1+"");
        System.out.println((int) Math.pow(2,31)+"");
        System.out.println((int) Math.pow(2,30)*2+"");
        System.out.println((int) Math.pow(2,30)*-2 +"");
        System.out.println(Integer.toBinaryString(-5));//补码  将00000000 00000000 00000000 00000101每一位取得到反码  11111111111111111111111111111010 反码加1  11111111111111111111111111111011
        System.out.println("-1:"+Integer.toBinaryString(-1));//补码  将00000000 00000000 00000000 00000001每一位取反加1  11111111111111111111111111111111

        //为什么负数时-2^31 正数是2^31-1
        //-1在计算机中 是1+31个1  -2^32在计算机中 是 1+31个0

        System.out.println("-2^32:"+Integer.toBinaryString((int)(-Math.pow(2,31))));//最小负数

        System.out.println("-2^32:"+Integer.toBinaryString(-(int)(Math.pow(2,31))));//先强制转换 (int)(Math.pow(2,31)) 得到的值其实是 2^31 -1 这是最大的正数然后转换成-（2^32 -1）

        System.out.println(Integer.toBinaryString((int) Math.pow(2,30)*2));//一个31个0
        System.out.println(Integer.toBinaryString((int) Math.pow(2,31)));//31个1  少了1 理论上是 1+31个0 因为第32位是符号位所以溢出了

        System.out.println(Integer.toBinaryString(-(int) Math.pow(2,16)-1));
//        System.out.println(Test());


    }

    private static String Test(){

        int source=4564654;//&0xf4f5F5f1;
        System.out.println(Integer.toBinaryString(source));
        byte[] result = new byte[4];
        result[0] = (byte)1;
        result[1] = (byte) ((source >> 16) & 0xFF);
        result[2] = (byte) ((source >> 8) & 0xFF);
        result[3] = (byte) (source & 0xFF);

//        byte a=127;
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<result.length;i++){

//            stringBuffer.append(Integer.toBinaryString(result[i]));
            stringBuffer.append(byte2bits(result[i]));
            stringBuffer.append("\n");
        }
        return  stringBuffer.toString();
    }

    private static String byteToIntTest(byte i){
        int a=i;
        return a+"";
    }



    private static String byte2bits(byte b) {
        int z = b; z |= 256;//256 是 100000000
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len-8, len);
    }
}
