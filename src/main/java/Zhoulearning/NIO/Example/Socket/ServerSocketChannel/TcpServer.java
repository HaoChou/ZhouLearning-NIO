package Zhoulearning.NIO.Example.Socket.ServerSocketChannel;

import Zhoulearning.NIO.Example.Socket.SocketChannel.TCPProtocolImpl;
import Zhoulearning.NIO.Example.Socket.SocketChannel.TCPProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class TcpServer {

    private static final int BUFFER_SIZE = 1024;
    private static final int TIME_OUT = 3000;//
    private static final int LISTEN_PORT = 1978;

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(LISTEN_PORT));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        TCPProtocol tcpProtocol = new TCPProtocolImpl(BUFFER_SIZE);

        while (true) {

//            if (selector.select(TIME_OUT) == 0) {//select()方法返回的int值表示有多少通道已经就绪
//
//                System.out.println("wait!");
//                continue;
//            }
            selector.select();
            System.out.println("SELECTED!");
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                try {


                    if (selectionKey.isAcceptable()) {
                        System.out.println("isAcceptable!");
                        // 有客户端连接请求时
                        tcpProtocol.handleAccept(selectionKey);

                    } else if (selectionKey.isConnectable()) {
                        System.out.println("isConnectable!");
                        // a connection was established with a remote server.

                    } else if (selectionKey.isReadable()) {
                        System.out.println("isReadable!");
                        // 从客户端读取数据
                        tcpProtocol.handleRead(selectionKey);
                    } else if ((selectionKey.isValid() && selectionKey.isWritable())) {
                        // 客户端可写时
                        System.out.println("isWriteable");
                        tcpProtocol.handleWrite(selectionKey);
                    }
                } catch (IOException ex) {
                    // 出现IO异常（如客户端断开连接）时移除处理过的键
                    selectionKey.cancel();
                    keyIterator.remove();

                    System.out.println(ex.toString());
                    continue;
                }
                    keyIterator.remove();

            }


        }//while end

    }
}
