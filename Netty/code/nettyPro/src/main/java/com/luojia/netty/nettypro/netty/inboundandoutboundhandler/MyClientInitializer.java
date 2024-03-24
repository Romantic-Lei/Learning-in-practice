package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个出站的 handler 对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder());
        // 加入一个入站的 handler 对数据进行一个解码
        // pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongReplayDecoder());
        // 加入一个自定义的 handler， 处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
