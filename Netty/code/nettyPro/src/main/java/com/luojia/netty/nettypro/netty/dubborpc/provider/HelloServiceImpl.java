package com.luojia.netty.nettypro.netty.dubborpc.provider;

import com.luojia.netty.nettypro.netty.dubborpc.publicinterface.HelloService;

/**
 * 服务提供者，service
 */
public class HelloServiceImpl implements HelloService {

    // 当有消费方调用该方法时，就返回一个结果
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息 = " + msg);
        if (null != msg) {
            return "你好客户端，我已收到你的消息，内容是： " + msg;
        } else {
            return "你好客户端，消息内容为空";
        }
    }
}
