package com.romanticLei;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Romantic-Lei
 * @version 1.0
 * @date 2020/11/17
 */
public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // 带入一个银行办理业务的案例来模拟我们的AQS如何进行线程的管理和通知唤醒机制

        // 3个线程模拟3个银行网点，受理窗口办理业务的顾客

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("-----A thread come in");
                // 线程暂停整分钟
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } finally {
                lock.unlock();
            }
        }, "A").start();

        // 第2个顾客
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("-----B thread come in");
            } finally {
                lock.unlock();
            }
        }, "B").start();

        // 第3个顾客
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("-----C thread come in");
            } finally {
                lock.unlock();
            }
        }, "C").start();

    }
}
