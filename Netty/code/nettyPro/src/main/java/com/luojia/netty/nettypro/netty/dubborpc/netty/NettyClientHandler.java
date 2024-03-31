package com.luojia.netty.nettypro.netty.dubborpc.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    // 上下文
    private ChannelHandlerContext context;
    // 返回的结果
    private String result;
    // 客户端调用方法时，传入的参数
    private String param;

    // 与服务器的连接创建后，就会被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    // 收到服务器数据后会被调用
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); // 唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    // 被代理对象调用，发送数据给服务器，需要wait -> 等待被唤醒(channelRead) -> 返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait(); // 休眠等待 channelRead 方法获取到服务器的结果后，唤醒
        return result; // 返回服务方发送的结果
    }

    public void setParam(String param) {
        this.param = param;
    }
}
