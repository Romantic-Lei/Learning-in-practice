package com.juc.lock.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class SaleTickDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {for (int i = 0; i < 55; i++) ticket.sale();}, "a").start();
        new Thread(() -> {for (int i = 0; i < 55; i++) ticket.sale();}, "b").start();
        new Thread(() -> {for (int i = 0; i < 55; i++) ticket.sale();}, "c").start();
    }
}

class Ticket {
    // 资源类，模拟3个售票员卖完50张票
    private int number = 50;
    ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第：\t" + number-- + "\t还剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}
