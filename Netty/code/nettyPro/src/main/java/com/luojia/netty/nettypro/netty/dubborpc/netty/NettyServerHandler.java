package com.luojia.netty.nettypro.netty.dubborpc.netty;

import com.luojia.netty.nettypro.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.luojia.netty.nettypro.netty.dubborpc.customer.ClientBootstrap.providerName;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息， 并调用服务器
        String msg0 = msg.toString();
        System.out.println("msg = " + msg0);
        // 客户端在调用服务端时，我们需要约定一个协议
        // 比如我没要求每次发送消息时，都已某个指定支付串开头 "HelloService#hello#"
        if (msg0.startsWith(providerName)) {
            String msg1 = msg0.substring(providerName.length());
            String res = new HelloServiceImpl().hello(msg1);
            ctx.writeAndFlush(res);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("创建链接~");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
