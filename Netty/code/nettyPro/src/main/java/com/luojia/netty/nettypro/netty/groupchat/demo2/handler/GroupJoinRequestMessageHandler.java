package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupCreateResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupJoinRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupJoinResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.Group;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.GroupSession;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        Group group = groupSession.joinMember(groupName, username);
        if (null == group) {
            // 发送群不存在消息
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, groupName + "不存在"));
        } else {
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupJoinResponseMessage(true, username + "加入群聊 " + groupName));
            }
        }
    }
}
