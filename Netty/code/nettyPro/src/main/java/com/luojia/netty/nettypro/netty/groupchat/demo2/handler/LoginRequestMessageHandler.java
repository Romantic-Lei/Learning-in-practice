package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.LoginRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.LoginResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.ChatServer;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.service.UserServiceFactory;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        // 登录成功，绑定 Channel和用户直接的关系
        if (login) {
            // 绑定会话，将用户名和会话绑定
            SessionFactory.getSession().bind(ctx.channel(), username);
            message = new LoginResponseMessage(true, "登录成功");
        } else {
            message = new LoginResponseMessage(false, "用户名或密码错误");
        }
        ctx.writeAndFlush(message);
    }
}
