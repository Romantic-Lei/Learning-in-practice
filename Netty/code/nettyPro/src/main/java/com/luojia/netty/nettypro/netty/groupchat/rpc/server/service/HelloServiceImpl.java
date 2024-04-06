package com.luojia.netty.nettypro.netty.groupchat.rpc.server.service;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        /*
        不做处理的话：爆出：io.netty.handler.codec.TooLongFrameException: Adjusted frame length exceeds 1024: 11509 - discarded
        处理地方：RpcRequestMessageHandler ------42行
         */
//        int i = 1 / 0;
        return "服务端ROBOT ：你好, " + msg;
    }
}