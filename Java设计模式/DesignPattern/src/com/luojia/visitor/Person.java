package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public abstract class Person {

    // 提供一个方法，让访问者可以访问
    public abstract void accept(Action action);

}
