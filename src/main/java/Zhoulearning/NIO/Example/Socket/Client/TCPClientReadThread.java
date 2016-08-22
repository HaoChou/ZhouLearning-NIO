package Zhoulearning.NIO.Example.Socket.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class TCPClientReadThread implements Runnable {

    private Selector selector;

    public TCPClientReadThread(Selector selector) {
        this.selector = selector;
        new Thread(this).start();
    }


    @Override
    public void run() {

        System.out.println("TCPClientReadThread started!");
        try {

            while (true) {

             int i =   selector.select();
                System.out.println("each11**************"+i);
                if (i > 0) {
                    for (SelectionKey key : selector.selectedKeys()) {
                        if (key.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            socketChannel.read(byteBuffer);//读取信道 到缓存
                            byteBuffer.flip();
                            String recevedString = Charset.forName("UTF-8")
                                    .newDecoder().decode(byteBuffer).toString();
                            System.out.println("接受来自服务器"
                                    + socketChannel.socket().getRemoteSocketAddress()
                                    + "的信息：" + recevedString);
                            key.interestOps(SelectionKey.OP_READ);
                        }
                        // 删除正在处理的SelectionKey
                        selector.selectedKeys().remove(key);
                    }
                }

                System.out.println("each**************");

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("TCPClientReadThread Error!"+e.toString());
        }
    }
}
