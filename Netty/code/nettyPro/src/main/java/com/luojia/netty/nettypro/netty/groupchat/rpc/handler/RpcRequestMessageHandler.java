package com.luojia.netty.nettypro.netty.groupchat.rpc.handler;

import com.luojia.netty.nettypro.netty.groupchat.rpc.message.RpcRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {

    }

    public static void main(String[] args) {
        RpcRequestMessage message = new RpcRequestMessage(
                1,
                "com.luojia.netty.nettypro.netty.groupchat.rpc.service.service.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"}
        );
    }
}
