package com.luojia.netty.nettypro.netty.groupchat.demo2.server;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.LoginRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.LoginResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.protocol.MessageCodecSharable;
import com.luojia.netty.nettypro.netty.groupchat.demo2.protocol.ProcotolFrameDecoder;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.service.UserServiceFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                    String username = msg.getUsername();
                                    String password = msg.getPassword();
                                    boolean login = UserServiceFactory.getUserService().login(username, password);
                                    log.debug("login:{}", login);
                                    LoginResponseMessage message;
                                    if (login) {
                                        message = new LoginResponseMessage(true, "登录成功");
                                    } else{
                                        message = new LoginResponseMessage(false, "用户名或密码错误");
                                    }
                                    ctx.writeAndFlush(message);
                                }
                            });
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

}
