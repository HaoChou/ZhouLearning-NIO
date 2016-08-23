package Zhoulearning.NIO.Example.Socket.Client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class TCPClient {

    private Selector selector;
    SocketChannel socketChannel;
    private String serverIp;
    private int serverListenningPort;

    public TCPClient(String serverIp, int serverListenningPort) throws InterruptedException, IOException {
        this.serverIp = serverIp;
        this.serverListenningPort = serverListenningPort;
        initialize();
    }

    /**
     * initialize
     *
     * @throws IOException
     */
    private void initialize() throws InterruptedException, IOException {
        do {
            try {
                socketChannel = SocketChannel.open(new InetSocketAddress(serverIp
                        , serverListenningPort));
            } catch (IOException e) {
                System.out.println("连接服务器" + serverIp + serverListenningPort + "失败！");
                Thread.sleep(2000);
                System.out.println("重新连接中......");
            }
        } while (null == socketChannel);

        System.out.println("服务器连接成功!");
        socketChannel.configureBlocking(false);
        selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_READ);

        new TCPClientReadThread(selector);

    }

    /**
     * send message
     *
     * @param message
     * @throws IOException
     */
    public void sendMSG(String message) throws IOException {
        ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
        socketChannel.write(writeBuffer);
    }

    static TCPClient client;
    static boolean mFlag = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        client = new TCPClient("127.0.0.1", 1978);
        new Thread() {
            @Override
            public void run() {
                try {
                    client.sendMSG("你好！NIO！");
                    while (mFlag) {
                        Scanner scan = new Scanner(System.in);//键盘输入数据
                        String string = scan.next();
                        client.sendMSG(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mFlag = false;
                }
                super.run();
            }
        }.start();
    }

}
