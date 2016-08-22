package Zhoulearning.NIO.Example.datagramChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by Zhou on 2016/8/23.
 */
public class UDPAcceptor {

    public static void main(String[] args) throws IOException {
        DatagramChannel channel=DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        while (true){
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            channel.receive(buf);  //会阻塞在这里等待接收
            buf.flip();
            System.out.println( Charset.forName("UTF-8").newDecoder().decode(buf).toString());
        }
    }
}
