package com.juc.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderAPIDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
        longAccumulator.accumulate(1);// 1
        longAccumulator.accumulate(3);// 4

        System.out.println(longAccumulator.get());
    }
}
