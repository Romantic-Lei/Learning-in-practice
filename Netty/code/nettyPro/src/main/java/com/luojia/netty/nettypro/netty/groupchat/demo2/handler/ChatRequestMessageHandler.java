package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.ChatRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.ChatResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        // 获取消息接受方的Channel 信息
        Channel channel = SessionFactory.getSession().getChannel(to);
        // 判断消息接收方是否在线
        if (null != channel) {
            // 向消息接收方发送消息
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
        } else {
            // 向消息发送方发送消息，提示接收方未上线
            ctx.writeAndFlush(new ChatResponseMessage(false, "对方用户暂未上线"));
        }

    }
}
