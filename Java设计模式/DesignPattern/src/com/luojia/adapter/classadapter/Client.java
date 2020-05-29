package com.luojia.adapter.classadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("类适配器模式");
        Phone phone = new Phone();
        phone.charging(new VoltageAdapter());
    }
}
