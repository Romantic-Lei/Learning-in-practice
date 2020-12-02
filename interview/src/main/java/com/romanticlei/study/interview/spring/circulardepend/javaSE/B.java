package com.romanticlei.study.interview.spring.circulardepend.javaSE;

public class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B() {
        System.out.println("创建B成功");
    }
}
