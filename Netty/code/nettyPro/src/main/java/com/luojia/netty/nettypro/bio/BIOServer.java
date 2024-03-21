package com.luojia.netty.nettypro.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 创建 serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true) {
            System.out.println("等待连接");
            // 监听，等待客户端连接, BIO,一直阻塞在等待连接部分
            Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    // 编写一个handler和客户端通信
    public static void handler(Socket socket) {
        System.out.println("线程信息 ID = " + Thread.currentThread().getId() +
                "线程名字 = " + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        try (InputStream inputStream = socket.getInputStream()) {
            // 循环读取客户端发送的请求
            while (true) {
                System.out.println("等待读取...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
