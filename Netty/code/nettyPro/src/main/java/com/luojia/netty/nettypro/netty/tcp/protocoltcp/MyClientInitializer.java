package com.luojia.netty.nettypro.netty.tcp.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageDecoder()); // 加入解码器
        pipeline.addLast(new MyMessageEncoder()); // 加入编码器
        pipeline.addLast(new MyClientHandler());
    }
}
