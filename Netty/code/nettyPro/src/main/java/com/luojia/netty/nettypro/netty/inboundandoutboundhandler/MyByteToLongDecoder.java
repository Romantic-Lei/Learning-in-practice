package com.luojia.netty.nettypro.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decode 会根据接收的数据，被调用多次，直到确定没有新的元素被添加到list
     * 或者是 byteBuf 没有更多的可读字节为止
     * 如果 list 不为空，就会将list的内容传递给下一个 ChannelInboundHandler 处理，
     * 该处理器的方法也会被多次调用
     * 例如：客户端发送 ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdefghefgh", CharsetUtil.UTF_8));
     * 有16字节，这里将会被调用两次，结果分别是：
     * MyByteToLongDecoder 服务端解码被调用
     * 从客户端 /127.0.0.1:54579 读取到long 7017280452178371428
     * MyByteToLongDecoder 服务端解码被调用
     * 从客户端 /127.0.0.1:54579 读取到long 7306641143530678120
     *
     * 入站数据解码
     * @param ctx 上下文
     * @param byteBuf 入站的ByteBuf
     * @param list List集合，将解码后的数据传给下一个 handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder 服务端解码被调用");
        // 因为 long 是8个字节，需要判断有8个字节，才能读取一个long
        if (byteBuf.readableBytes() >= 8) {
            // 这里将一个long字节读取出来
            list.add(byteBuf.readLong());
        }
    }
}
