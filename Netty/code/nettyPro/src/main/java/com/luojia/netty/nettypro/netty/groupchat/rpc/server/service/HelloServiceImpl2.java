package com.luojia.netty.nettypro.netty.groupchat.rpc.server.service;

public class HelloServiceImpl2 implements HelloService{
    @Override
    public String sayHello(String msg) {
        return "啥都不知道 ： " + msg;
    }
}
