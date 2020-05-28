package com.luojia.singleton;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description
 */
public class singletonTest07 {
    public static void main(String[] args) {
        Singleton07 instance = Singleton07.INSTANCE;
        Singleton07 instance1 = Singleton07.INSTANCE;
        System.out.println(instance == instance1);

        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());

        instance.enumOk();
    }
}

enum Singleton07{
    INSTANCE;

    public void enumOk() {
        System.out.println("枚举类，懒汉安全");
    }
}