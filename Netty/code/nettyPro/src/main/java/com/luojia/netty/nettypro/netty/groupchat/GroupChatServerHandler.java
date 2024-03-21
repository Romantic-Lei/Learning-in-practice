package com.luojia.netty.nettypro.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个Channel组，管理所有的Channel
    // GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示链接建立，第一个被执行
    // 将当前Channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其他在线的客户端
        // channelGroup.writeAndFlush 该方法会将 channelGroup 中所有的channel 遍历，并发送消息
        channelGroup.writeAndFlush(sdf.format(new Date()) + " [客户端]" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
    }

    // 断开链接，将 XXX 客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + " [客户端]" + channel.remoteAddress() + "离开聊天\n");
        // 此处不用手动的remove 离线的channel，它会自动帮我们remove
        // channelGroup.remove(channel);
        System.out.println("channelGroup size: " + channelGroup.size());
    }

    // 表示 channel 处于活动状态，提示 XXX上线了
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    // 表示 channel 处于不活动状态，提示 XXX离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取到当前 channel
        Channel channel = ctx.channel();
        // 遍历 channelGroup,根据不同的情况发送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                // 不是当前客户端，需要转发消息
                ch.writeAndFlush(sdf.format(new Date()) + " [客户端：] " + channel.remoteAddress() + " 发送了消息： " + msg + "\n");
            } else {
                // 当前channel是自己，不用处理
                ch.writeAndFlush(sdf.format(new Date()) + " [回显：] " + msg + "\n");
                System.out.println("当前channel是自己，不用处理");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }

}
