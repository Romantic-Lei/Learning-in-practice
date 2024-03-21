package com.luojia.netty.nettypro.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

    private int port; // 监听端口
    
    public GroupChatServer(int port) {
        this.port = port;
    }
    
    // 编写run 方法，处理客户端的请求
    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取到 Pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向 pipeline 加入解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向 pipeline 加入编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入自己业务的处理器
                            pipeline.addLast("myGroupChatServerHandler", new GroupChatServerHandler());
                        }
                    });

            System.out.println("netty 服务器已启动");
            ChannelFuture future = serverBootstrap.bind(port).sync();

            // 监听关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(7001).run();
    }

}
