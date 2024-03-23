package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    // 编码方法
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder encode 被调用");
        System.out.println("msg = " + msg);
        out.writeLong(msg);
    }
}
