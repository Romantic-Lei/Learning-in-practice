package com.luojia.adapter.objectadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("对象适配器模式");
        Phone phone = new Phone();
        phone.charging(new VoltageAdapter(new Voltage()));
    }
}
