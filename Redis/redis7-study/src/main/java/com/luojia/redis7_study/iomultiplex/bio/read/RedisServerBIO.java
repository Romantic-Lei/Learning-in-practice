package com.luojia.redis7_study.iomultiplex.bio.read;

import cn.hutool.core.util.IdUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServerBIO {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);

        while (true) {
            System.out.println("-----111 等待连接");
            // 阻塞1，等待客户端连接
            Socket socket = serverSocket.accept();
            System.out.println("-----222 成功连接");

            InputStream inputStream = socket.getInputStream();
            int length = -1;
            byte[] bytes = new byte[1024];
            System.out.println("-----333 等待读取");
            // 阻塞2，等待客户端发送数据
            while ((length = inputStream.read(bytes)) != -1) {
                System.out.println("---444 成功读取" + new String(bytes, 0, length));
                System.out.println("=============" + "\t" + IdUtil.simpleUUID());
                System.out.println();
            }
            inputStream.close();
            socket.close();

        }

    }
}
