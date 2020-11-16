package com.romanticLei;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Romantic-Lei
 * @version 1.0
 * @date 2020/11/13
 */
public class LockSupportDemo {

    static Object objectLock = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void synchronizedWaitNotify(){
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t" + "come in");
                try {
                    // wait需要和synchronized一起使用
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "被唤醒");
        }, "A").start();

        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName() + "\t" + "通知");
                objectLock.notify();
            }
        }, "B").start();
    }

    public static void lockAwaitSignal(){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
            try {
                try {
                    // await需要和lock一起使用
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "-----通知");
            } finally {
                lock.unlock();
            }
        }, "B").start();

    }

    public static void lockSupportParkUnpark(){
        Thread a = new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
            LockSupport.park(); // 被阻塞，等待通知，等待放行
            System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
        }, "a");
        a.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        Thread b = new Thread(() -> {
            LockSupport.unpark(a); // 被阻塞，等待通知，等待放行
            System.out.println(Thread.currentThread().getName() + "\t" + "-----b 发出唤醒通知了");
        }, "b");
        b.start();

    }

    public static void main(String[] args) {
//        synchronizedWaitNotify();
//        lockAwaitSignal();
        lockSupportParkUnpark();
    }


}
