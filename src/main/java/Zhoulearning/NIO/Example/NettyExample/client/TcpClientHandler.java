package Zhoulearning.NIO.Example.NettyExample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhouhao9 on 2016/8/24.
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        //messageReceived方法,名称很别扭，像是一个内部方法.
        System.out.println("client接收到服务器返回的消息:" + o);
        channelHandlerContext.channel().writeAndFlush("hehe");
    }
}
