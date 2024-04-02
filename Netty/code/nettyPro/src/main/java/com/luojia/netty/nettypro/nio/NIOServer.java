package com.luojia.netty.nettypro.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel -> serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个Selector 对象
        Selector selector = Selector.open();
        // 绑定一个端口6666，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞

        // 把 serverSocketChannel 注册到Selector 关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.configureBlocking(false);
        // 循环等待客户链接
        while (true) {
            // 非阻塞，立即返回
            // if (selector.selectNow() == 0) {
            // select,阻塞指定时间毫秒，无链接就阻塞指定时间返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            // 如果返回的 >0，就获取到了selectionKey集合
            // 1.如果返回大于0，表示已经获取到了关注的集合
            // 2.selector.selectedKeys()返回关注时间的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 通过 selectionKeys 反向获取通道
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 根据key，对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) {
                    // serverSocketChannel的accept()是不阻塞的
                    SocketChannel sockerChannel = serverSocketChannel.accept();
                    sockerChannel.configureBlocking(false);
                    // 将SocketChannel 注册到Selector，关注事件为 OP_READ
                    // register 的第三个参数是附件的意思，将每个连接的 SocketChannel 与每次读取的大小进行绑定
                    sockerChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 发生事件 OP_READ
                if (key.isReadable()) {
                    // 通过key，反向获取到Channel
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    // 获取到该Channel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    socketChannel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));
                }

                // 手动从集合中移动当前的selectionKey，防止迭代器中重复遍历
                keyIterator.remove();
            }
        }
    }
}
