package com.luojia.netty.nettypro.netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        // 需要将得到的二进制字节码 -> MessageProtocol 数据包(对象)
        int len = byteBuf.readInt();
        byte[] content = new byte[len];
        // 将 byteBuf 中数据读取到 content 中
        byteBuf.readBytes(content);

        // 封装成 MessageProtocol 对象，放入 list，传递到下一个handler 业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        list.add(messageProtocol);
    }
}
