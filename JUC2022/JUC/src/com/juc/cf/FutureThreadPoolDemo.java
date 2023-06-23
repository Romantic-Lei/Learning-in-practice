package com.juc.cf;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // ThreadNum3();
        // FutureBlock();
        FutureDone();
    }

    public static void FutureDone() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        threadPool.submit(futureTask);
        System.out.println("-----执行其他任务");
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("异步线程暂未执行完毕");
            }
        }
        threadPool.shutdown();
    }

    public static void FutureBlock() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(5);
            return "task over";
        });
        threadPool.submit(futureTask);
        System.out.println("-----执行其他任务");
        futureTask.get(3, TimeUnit.SECONDS);
        threadPool.shutdown();
    }

    public static void ThreadNum3() throws ExecutionException, InterruptedException {
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
