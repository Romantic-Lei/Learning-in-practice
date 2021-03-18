package com.romanticLei;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    static Lock lock = new ReentrantLock();
    public void sycMethod(){
        lock.lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "----外层调用lock");
            lock.lock();
            try {
                System.out.println("----内层调用lock");
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
            // 有lock加锁就需要有相应的解锁，不然会造成死锁
            // 如果解锁次数多于加锁次数 会出现异常java.lang.IllegalMonitorStateException
            // 此处故意让加锁次数和解锁次数不一致，让后面线程等待
            // lock.unlock();
        }
    }

    public static void main(String[] args) {
       // m1();
       // new ReEnterLockDemo().m2();
        new Thread(() -> {
            new ReEnterLockDemo().sycMethod();
        }, "t1").start();

        new Thread(() -> {
            new ReEnterLockDemo().sycMethod();
        }, "t2").start();
    }
}
