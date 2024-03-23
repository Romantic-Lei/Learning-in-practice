package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 入站的handler 进行解码 MyByteToLongDecoder
        pipeline.addLast("MyByteToLongDecoder", new MyByteToLongDecoder());
        // 自定义的 handler 处理业务逻辑
        pipeline.addLast("", new MyServerHandler());
    }
}
