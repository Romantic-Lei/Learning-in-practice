package com.juc.cf;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            TimeUnit.MICROSECONDS.sleep(500);
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            TimeUnit.MICROSECONDS.sleep(300);
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        // 加上下面这两个获取异步线程的结果，会比不获取结果要耗时一点但是也比完全同步执行耗时强很多
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());

        FutureTask<String> futureTask3 = new FutureTask<String>(() -> {
            TimeUnit.MICROSECONDS.sleep(300);
            return "task3 over";
        });
        threadPool.submit(futureTask3);
        long endTime = System.currentTimeMillis();
        System.out.println("-------costTime: " + (endTime - startTime) + "毫秒");

        threadPool.shutdown();
    }
}
