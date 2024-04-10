package com.luojia.netty.nettypro.netty.basicapi;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.luojia.netty.nettypro.netty.basicapi.TestByteBuf.log;

public class TestSlice {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        // abcdefghij
        log(buf);

        // 在切片过程中，没有发生数据复制，相当于使用的还是原来的内存
        // 切片后的 ByteBuf 容量已经固定，不支持向后追加数据
        ByteBuf f1 = buf.slice(0, 5);
        ByteBuf f2 = buf.slice(5, 5);
        // abcde
        log(f1);
        // fghij
        log(f2);

        System.out.println("=================");
        f1.setByte(0, 'b');
        // bbcdefghij
        log(buf);
        // bbcde
        log(f1);

        System.out.println("释放原有的 byteBuf 内存");
        buf.release();
        // 原来的 buf 内存释放后，此处在使用会报错
        log(f1);
    }
}
