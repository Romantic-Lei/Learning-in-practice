package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyByteToLongReplayDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongReplayDecoder 解码被调用");
        // if (byteBuf.readableBytes() >= 8) {
        //     // 这里将一个long字节读取出来
        //     list.add(byteBuf.readLong());
        // }
        // 在 ReplayingDecoder 中不需要判断数据是否足够读取，内部会进行处理判断
        list.add(byteBuf.readLong());
    }
}
