package com.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar {
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> referenceFieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    public void init(MyVar myVar) throws InterruptedException {
        if (referenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + "start init : need 2 seconds" );
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + "over init" );
        } else {
            System.out.println(Thread.currentThread().getName() + "已经完成初始化" );
        }
    }
}

/**
 * 需求：
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，
 * 要求只能被初给化一次，只有一个线程操作成功
 */
public class AtomicReferenceFieldUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    myVar.init(myVar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, String.valueOf(i)).start();
        }
    }
}
