package com.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        // AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        // AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        int tmpInt = 0;
        // 设置下标为1的数据，值为1122
        tmpInt = atomicIntegerArray.getAndSet(0, 1122);
        System.out.println("tmpInt = " + tmpInt + "\t" + atomicIntegerArray.get(0));

        tmpInt = atomicIntegerArray.getAndIncrement(0);
        System.out.println("tmpInt = " + tmpInt + "\t" + atomicIntegerArray.get(0));
    }

}
