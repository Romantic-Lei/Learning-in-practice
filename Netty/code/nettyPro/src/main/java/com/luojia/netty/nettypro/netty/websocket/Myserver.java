package com.luojia.netty.nettypro.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Myserver {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 因为基于http协议，使用http 的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加 ChunkedWriteHandler 处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * http 数据在传输过程中是分段的，HttpObjectAggregator 就是将多个段聚合
                             * 这就是为什么，当浏览器发送大量数据时，就会发出多次http 请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * 对应websocket，它的数据时以 帧(frame) 形式传递
                             * 可以看到websocketFrame 下面有六个子类
                             * 浏览器请求时：ws://localhost:7000/hello 表示请求的uri
                             * WebSocketServerProtocolHandler 核心功能是将 http 协议升级为 ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 自定义的 handler，处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            // 启动服务器
            ChannelFuture future = serverBootstrap.bind(7001).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
