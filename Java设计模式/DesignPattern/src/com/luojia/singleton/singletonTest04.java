package com.luojia.singleton;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description 懒汉式, 线程安全
 */
public class singletonTest04 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance == instance1);
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance1.hashCode=" + instance1.hashCode());
    }
}

class Single04 {
    private static Single04 instance;

    private Single04() {
    }

    // 提供一个静态的公有方法，加入同步处理的代码，解决线程安全的问题
    // 即懒汉式
    public static synchronized Single04 getInstance() {
        if (instance == null) {
            instance = new Single04();
        }
        return instance;
    }

}
