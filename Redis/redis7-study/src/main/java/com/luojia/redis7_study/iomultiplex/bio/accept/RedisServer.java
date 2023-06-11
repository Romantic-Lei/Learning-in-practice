package com.luojia.redis7_study.iomultiplex.bio.accept;

import cn.hutool.core.util.IdUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);

        while (true) {
            System.out.println("模拟RedisServer启动-----111 等待连接");
            Socket accept = serverSocket.accept();
            System.out.println("-----111 连接成功： " + IdUtil.simpleUUID());
            System.out.println();
        }
    }
}
