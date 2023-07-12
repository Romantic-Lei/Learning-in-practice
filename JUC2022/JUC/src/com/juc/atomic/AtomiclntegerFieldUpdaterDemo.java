package com.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount {
    String bankName = "CCB";

    // 更新的对象属性必须使用 public volatile修饰符。
    public volatile int money = 0;// 钱数

    public synchronized void add() {
        money++;
    }

    // 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法newUpdater()
    // 创建一个更新器，并且需要设置想要更新的类和属性。
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    // 不加synchronized， 保证高性能原子性
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}

/**
 * 以一种规程安全的方式操作非线程安全对象的某些字段。
 * 需求：
 * 10个线程。
 * 每个线程转账1000，
 * 不使用Synchronized,尝试使用AtomicIntegerFieldupdater来实现。
 */
public class AtomiclntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    bankAccount.transMoney(bankAccount);
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "result: " +bankAccount.money);
    }
}
