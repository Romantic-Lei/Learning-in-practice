package com.luojia.netty.nettypro.netty.groupchat.demo2.client;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.LoginRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.protocol.MessageCodecSharable;
import com.luojia.netty.nettypro.netty.groupchat.demo2.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    // 连接建立后，触发 active 事件，另起一个新线程来发送各种消息，避免阻塞worker线程
                                    new Thread(() -> {
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("请输入用户名：");
                                        String username = scanner.nextLine();
                                        System.out.println("请输入密码：");
                                        String password = scanner.nextLine();
                                        // 构造消息对象
                                        LoginRequestMessage message = new LoginRequestMessage(username, password);
                                        // 发送消息
                                        ctx.writeAndFlush(message);
                                        System.out.println("等待后续操作");
                                        try {
                                            System.in.read();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }, "system in").start();
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.debug("msg: {}", msg);
                                    super.channelRead(ctx, msg);
                                }
                            });
                        }
                    });
            Channel channel = bootstrap.connect("localhost", 7001).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

}
