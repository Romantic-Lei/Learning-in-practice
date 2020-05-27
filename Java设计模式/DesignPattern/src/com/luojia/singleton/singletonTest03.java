package com.luojia.singleton;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description 懒汉式, 线程不安全
 */
public class singletonTest03<main> {

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance == instance1);
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance1.hashCode=" + instance1.hashCode());
    }
}

class Single03 {
    private static Single03 instance;

    private Single03() {
    }

    // 提供一个静态的公有方法，当使用到该方法时，才会创建instance
    // 即懒汉式
    public static Single03 getInstance() {
        if (instance == null) {
            instance = new Single03();
        }
        return instance;
    }
}