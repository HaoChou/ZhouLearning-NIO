package Zhoulearning.NIO.Example.datagramChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

/**
 * Created by Zhou on 2016/8/23.
 */
public class UDPSender {
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();

        String newData="Time is "+new Date().toString();
        ByteBuffer buf=ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        int bytesSent=channel.send(buf,new InetSocketAddress("127.0.0.1",9999));
        System.out.println("sent "+ bytesSent+" byte!");


        channel.close();
    }
}
