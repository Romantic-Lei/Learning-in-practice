package com.luojia.netty.nettypro.netty.groupchat.demo2.handler;

import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupQuitRequestMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.message.GroupQuitResponseMessage;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.Group;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.GroupSession;
import com.luojia.netty.nettypro.netty.groupchat.demo2.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        Group group = groupSession.removeMember(groupName, username);
        List<Channel> channels = groupSession.getMembersChannel(groupName);

        if (null == group) {
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, "群组不存在"));
        } else {
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupQuitResponseMessage(true, username + "退出群聊 " + groupName));
            }
        }
    }
}
