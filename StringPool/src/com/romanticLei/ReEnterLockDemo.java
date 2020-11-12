package com.romanticLei;

/**
 * @author Romantic-Lei
 * @version 1.0
 * @date 2020/11/11
 */
public class ReEnterLockDemo {

    static Object objectLockA = new Object();

    // 同步代码块
    public static void m1(){
        new Thread(() -> {
            synchronized (objectLockA){
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------外层调用");
                synchronized (objectLockA){
                    System.out.println(Thread.currentThread().getName()+"\t"+"-----------中层调用");
                    synchronized (objectLockA){
                        System.out.println(Thread.currentThread().getName()+"\t"+"-----------内层调用");
                    }
                }
            }
        }, "t1").start();
    }

    // 同步方法
    public synchronized void m2(){
        System.out.println("=====外");
        m3();
    }

    public synchronized void m3(){
        System.out.println("=====中");
        m5();
    }

    public synchronized void m5(){
        System.out.println("=====内");
    }


    public static void main(String[] args) {
        m1();
//        new ReEnterLockDemo().m2();
    }
}
