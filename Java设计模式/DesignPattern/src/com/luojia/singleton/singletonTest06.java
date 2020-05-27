package com.luojia.singleton;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description 懒汉式, 线程安全, 静态内部类
 */
public class singletonTest06 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance == instance1);
        System.out.println("instance.hashCode =" + instance.hashCode());
        System.out.println("instance1.hashCode=" + instance1.hashCode());
    }
}

class Single06 {
    private static volatile Single06 instance;

    private Single06() {}

    // 写一个静态内部类，该类中有一个静态属性Singleton
    private static class SingletonInstance{
        private static final Single06 INSTANCE = new Single06();
    }

    // 提供一个静态的公有方法，直接返回 SingletonInstance.INSTANCE
    // 即懒汉式
    public static Single06 getInstance() {

        return SingletonInstance.INSTANCE;
    }

}
