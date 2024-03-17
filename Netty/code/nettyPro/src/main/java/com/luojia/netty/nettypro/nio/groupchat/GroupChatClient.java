package com.luojia.netty.nettypro.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    // 定义相关属性
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        // socketChannel = SocketChannel.open();
        socketChannel = socketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        // socketChannel.connect(new InetSocketAddress(HOST, PORT));
        // 将SocketChannel注册到Selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 获取userName
        userName = socketChannel.getRemoteAddress().toString().substring(1);
        System.out.println(userName + " is ok...");
    }

    // 向服务器发送消息
    public void sendInfo(String info) {
        try {
            info = userName + "说：" + info;
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 从服务器端读取消息
    public void readInfo() {
        try {
            int intChannels = selector.select();
            if (intChannels > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    SocketChannel channel = (SocketChannel)key.channel();
                    channel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    String msg = new String(buffer.array());
                    System.out.println(msg.trim());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = new GroupChatClient();
        // 启动一个异步线程，读取从服务器发送的数据
        new Thread(() -> {
            while (true) {
                // 不断的读取数据
                chatClient.readInfo();
            }
        }).start();

        // 发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            chatClient.sendInfo(scanner.nextLine());
        }
    }
}
