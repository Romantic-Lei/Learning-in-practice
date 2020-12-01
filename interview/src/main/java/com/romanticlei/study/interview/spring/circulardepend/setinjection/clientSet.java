package com.romanticlei.study.interview.spring.circulardepend.setinjection;

public class clientSet {
    public static void main(String[] args) {
        // 创建ServiceAA
        ServiceAA a = new ServiceAA();
        // 创建ServiceBB
        ServiceBB b = new ServiceBB();

        // 将serviceA注入到ServiceB中
        b.setServiceAA(a);
        // 将ServiceB注入到ServiceA中
        a.setServiceBB(b);

    }
}
