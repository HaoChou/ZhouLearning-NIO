package Zhoulearning.NIO.Example.Socket.SocketChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by zhouhao9 on 2016/8/22.
 */
public class TCPProtocolImpl implements TCPProtocol {

    private int bufferSize;

    public TCPProtocolImpl(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        //返回创建此键的通道，接受客户端建立连接的请求，并返回 SocketChannel 对象
        SocketChannel clientcSocketChannel= ((ServerSocketChannel) key.channel())
                .accept();
        clientcSocketChannel.configureBlocking(false);
        //注册read的channel
        clientcSocketChannel.register(key.selector(), SelectionKey.OP_READ,
                ByteBuffer.allocate(bufferSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel= (SocketChannel) key.channel();
        ByteBuffer byteBuffer= (ByteBuffer) key.attachment();
        byteBuffer.clear();

        long bytesRead=clientSocketChannel.read(byteBuffer);
        if(bytesRead==-1){
            clientSocketChannel.close();
        }else {
            byteBuffer.flip();//换成读状态
            String receievedString = Charset.forName("UTF-8").
                    newDecoder().decode(byteBuffer).toString();
            System.out.println("接收来自："
                    +clientSocketChannel.socket().getRemoteSocketAddress()
                    +"的信息："+receievedString);

            String sendString ="你好，客户端，@"+new Date().toString()+
                    ",已收到你的信息："+receievedString;
            byteBuffer.wrap(sendString.getBytes("UTF-8"));
            clientSocketChannel.write(byteBuffer);

//            key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
          //  key.interestOps(SelectionKey.OP_READ);
//            key.interestOps(SelectionKey.OP_WRITE);
//            clientSocketChannel.register(key.selector(), SelectionKey.OP_READ|SelectionKey.OP_WRITE,ByteBuffer.allocate(bufferSize));
        }

    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.wrap("你好客户端！".getBytes("UTF-8"));
        clientSocketChannel.write(byteBuffer);
        key.interestOps(SelectionKey.OP_READ);


    }
}
