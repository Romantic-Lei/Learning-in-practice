package com.romanticLei;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
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

    public static void synchronizedAwaitSignal(){
        new Thread(() -> {
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

    public static void main(String[] args) {
//        synchronizedWaitNotify();
        synchronizedAwaitSignal();
    }

}
