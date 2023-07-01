package com.juc.lock;

public class LockSyncDemo {

    private final Object object = new Object();
    // public void m1() {
    //     synchronized (object) {
    //         System.out.println("---------hello synchronized code block---------");
    //     }
    // }

    // public synchronized void m2() {
    //     System.out.println("---------hello synchronized code block---------");
    // }

    public static synchronized void m3() {
        System.out.println("---------hello static synchronized code block---------");
    }

    public static void main(String[] args) {

    }
}
