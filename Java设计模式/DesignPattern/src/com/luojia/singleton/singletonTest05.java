package com.luojia.singleton;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/27
 * @description 懒汉式, 线程安全, 双重检查
 */
public class singletonTest05 {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();
        System.out.println(instance == instance1);
        System.out.println("instance.hashCode =" + instance.hashCode());
        System.out.println("instance1.hashCode=" + instance1.hashCode());
    }
}

class Single05 {
    private static volatile Single05 instance;

    private Single05() {
    }

    // 提供一个静态的公有方法，加入同步处理的代码，解决线程安全的问题
    // 即懒汉式
    public static Single05 getInstance() {
        if (instance == null) {
            synchronized (Single05.class){
                if (instance == null) {
                    instance = new Single05();
                }
            }
        }
        return instance;
    }

}

