package com.luojia.netty.nettypro.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int port = 6667;

    // 构造器，里面完成初始化工作
    public GroupChatServer() {
        try {
            // 获取到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(port));
            // 一定要设置成非阻塞的模式
            listenChannel.configureBlocking(false);
            // 将listenChannel 注册到Selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 开始监听
    public void listen() {
        // 一直监听

        try {
            while (true) {
                int count = selector.select(2000);
                // 如果事件>0，需要处理事件
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听到 accept
                        if (key.isAcceptable()) {
                            SocketChannel accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            // 将该链接注册到选择器 selector
                            accept.register(selector, SelectionKey.OP_READ);
                            // 提示上线
                            System.out.println(accept.getRemoteAddress() + " 上线了");
                        }

                        if(key.isReadable()) {
                            // 处理读方法
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待连接...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 获取到连接
            channel = (SocketChannel)key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0) {
                // 已经读取到了数据，把缓冲区数据转成字符串
                String msg= new String(buffer.array());
                System.out.println("from client：" + msg);
                // 向其他客户端转发消息，需要去掉自己
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 用户离线");
                // 取消注册
                key.channel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // 转发消息给其他客户端
    public void sendInfoToOtherClients(String msg, SocketChannel serverChannel) throws IOException {
        System.out.println("服务器准备群发消息...");
        for (SelectionKey key : selector.keys()) {
            // 通过key 拿到所有类型的Channel
            SelectableChannel targetChannel = key.channel();
            // 排除服务器自己的Channel链接
            if (targetChannel instanceof SocketChannel && !serverChannel.equals(targetChannel)) {
                // 转型
                SocketChannel dest = (SocketChannel)targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        // 创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
