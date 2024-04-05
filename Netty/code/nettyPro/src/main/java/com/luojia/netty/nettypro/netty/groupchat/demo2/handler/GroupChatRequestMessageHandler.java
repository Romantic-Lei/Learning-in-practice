package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupChatRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupChatResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.GroupSessionFactory;
import io.netty.channel.*;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        for (Channel channel : channels) {
            // 群聊消息，不需要将消息发送给发送方
            if (channel != ctx.channel()) {
                channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
            }
        }
    }
}
