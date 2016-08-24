package Zhoulearning.NIO.Example.NettyExample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

/**
 * Created by zhouhao9 on 2016/8/24.
 */
public class HelloServer {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9999;
    /**用于分配处理业务线程的线程组个数 */
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2; //默认
    /** 业务出现线程大小*/
    protected static final int BIZTHREADSIZE = 4;
    /*
 * NioEventLoopGroup实际上就是个线程池,
 * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
 * 每一个NioEventLoop负责处理m个Channel,
 * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
 */
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);




    protected static void run() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);  //(parentGroup,childGroup)
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //ChannelPipeline中的handler是可以在任何时候动态地增加或者删除的，因为ChannelPipeline本身是线程安全的
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new TcpServerHandler());
            }
        });

        b.bind(IP, PORT).sync();
        System.out.println("TCP服务器已启动");
    }

    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("开始启动TCP服务器...");
        HelloServer.run();
//      HelloServer.shutdown();
    }
}
