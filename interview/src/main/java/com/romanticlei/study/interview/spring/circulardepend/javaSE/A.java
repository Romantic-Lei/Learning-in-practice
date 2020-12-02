package com.romanticlei.study.interview.spring.circulardepend.javaSE;

public class A {

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public A() {
        System.out.println("创建A成功");
    }
}
