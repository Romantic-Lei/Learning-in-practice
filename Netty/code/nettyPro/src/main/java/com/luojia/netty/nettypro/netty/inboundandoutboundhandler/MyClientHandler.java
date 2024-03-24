package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器的 IP： ： " + ctx.channel().remoteAddress());
        System.out.println("收到服务器消息 = " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 连接上服务端发送数据");
        ctx.writeAndFlush(1234567890L);

        /**
         * 分析：
         * 1. "abcdabcdefghefgh" 是16个字节（一个字符串一个字节），服务端接受的事8字节，所以服务端的decode会被多次调用
         * 2. 该处理器的前一个 handler 是 MyLongToByteEncoder
         * 3. MyLongToByteEncoder 的父类是 MessageToByteEncoder
         * 4. 父类 MessageToByteEncoder有个 write() 方法，
         * 里面有个判断方法 this.acceptOutboundMessage(msg)，判断当前msg 是不是应该处理的类型，不是则不编码直接发送
         * 因此我们编写的Encoder 是要注意传入发数据类型和处理的数据类型要一致
         */
        // ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdefghefgh", CharsetUtil.UTF_8));
    }
}
