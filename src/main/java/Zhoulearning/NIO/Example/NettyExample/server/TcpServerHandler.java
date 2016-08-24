package Zhoulearning.NIO.Example.NettyExample.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhouhao9 on 2016/8/24.
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("服务器端收到："+o);
            channelHandlerContext.channel().writeAndFlush("yes, server is accepted you ,nice !"+o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        System.out.println("Unexpected exception from downstream." + cause);
        ctx.close();
    }
}
