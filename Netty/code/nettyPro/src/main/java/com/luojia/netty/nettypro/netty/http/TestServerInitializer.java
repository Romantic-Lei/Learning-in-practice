package com.luojia.netty.nettypro.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向官方中加入处理器
        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个netty 提供的 HttpServerCodec codec => [codec - decoder]
        // HttpServerCodec 说明
        // 1.HttpServerCodec 是netty 提供的处理http 的编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 2.增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}
