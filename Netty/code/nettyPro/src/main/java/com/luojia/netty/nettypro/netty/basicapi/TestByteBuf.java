package com.luojia.netty.nettypro.netty.basicapi;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class TestByteBuf {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        // class io.netty.buffer.PooledUnsafeDirectByteBuf
        // 池化的直接内存
        System.out.println(buffer.getClass());
        ByteBuf buffer1 = ByteBufAllocator.DEFAULT.heapBuffer(10);
        // class io.netty.buffer.PooledUnsafeHeapByteBuf
        // 池化的堆内存
        System.out.println(buffer1.getClass());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buffer.writeBytes(sb.toString().getBytes());
        log(buffer);
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
