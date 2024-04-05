package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupCreateRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupCreateResponseMessage;
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
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (null == group) {
            // 发送群聊创建成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
            // 发送拉群消息
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入" + groupName));
            }
        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, groupName + "群已经存在"));
        }

    }
}
