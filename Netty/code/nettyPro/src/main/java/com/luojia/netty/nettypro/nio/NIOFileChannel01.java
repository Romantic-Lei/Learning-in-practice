package com.luojia.netty.nettypro.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "你好，世界";
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file01.txt");

        // 通过fileOutputStream 获取对应的FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将数据写入到缓冲区
        byteBuffer.put( str.getBytes());
        // 对ByteBuffer 进行flip
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
