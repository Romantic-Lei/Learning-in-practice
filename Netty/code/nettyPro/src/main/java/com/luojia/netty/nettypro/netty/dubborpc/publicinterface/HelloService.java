package com.luojia.netty.nettypro.netty.dubborpc.publicinterface;

/**
 * 这是一个接口，是服务提供方和服务消费方都需要的
 */
public interface HelloService {
    public String hello(String msg);
}
