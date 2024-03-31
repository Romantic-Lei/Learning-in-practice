package com.luojia.netty.nettypro.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    // 定义一个线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static NettyClientHandler client;

    // 编写一个方法使用代理模式，获取一个代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    // {} 中的代码，客户端每调用一次 hello，就会进入到该代码
                    if (null == client) {
                        initClient();
                    }

                    // 设置要给服务器发送消息
                    client.setParam(providerName + args[0]);
                    return executor.submit(client).get();
                });
    }

    private static void initClient() {
        client = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(client);
                        }
                    });

            //直到连接返回，才会继续后面的执行，否则阻塞当前线程
            ChannelFuture future = bootstrap.connect("127.0.0.1", 7001).sync();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 如果没有 ChannelFuture future = serverBootstrap.bind(port).sync();
            // 则执行完连接后，马上就执行finally 块，关闭了连接
            // group.shutdownGracefully();
        }
    }

}
