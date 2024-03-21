package com.luojia.netty.nettypro.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String fileName = "F:\\nginx-1.23.4.zip";
        FileChannel channel = new FileInputStream(fileName).getChannel();
        
        // 准备发送
        long start = System.currentTimeMillis();
        // 在Linux下一个transferTo 方法就可以完成传输
        // 在Windows下一个transferTo 只能发送8M，需要分段传输文件，需要注意传输的位置
        // transferTo 底层使用到零拷贝
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送的总的字节数 = " + transferCount + "  耗时：" + (System.currentTimeMillis() - start));
        channel.close();
    }
}
