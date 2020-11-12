package com.romanticLei;

/**
 * @author Romantic-Lei
 * @version 1.0
 * @date 2020/11/11
 */
public class ReEnterLockDemo {

    static Object objectLockA = new Object();
    static Object objectLockB = new Object();

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

    public static void main(String[] args) {
        m1();
    }
}
