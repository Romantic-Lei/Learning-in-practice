package com.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

class ClickNumber {
    int number = 0;
    public synchronized void clickBysynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    public void clickAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();
    public void clickLongAdder() {
        longAdder.increment();
    }

}
/**
 * 需求：50个线程，每个线程100W次，求总点赞数
 */
public class AccumulatorCompreDemo {

    public static void main(String[] args) {

    }
}
