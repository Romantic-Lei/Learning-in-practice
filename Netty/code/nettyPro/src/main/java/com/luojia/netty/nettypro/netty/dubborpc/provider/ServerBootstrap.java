package com.luojia.netty.nettypro.netty.dubborpc.provider;

import com.luojia.netty.nettypro.netty.dubborpc.netty.NettyServer;

public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7001);
    }
}
