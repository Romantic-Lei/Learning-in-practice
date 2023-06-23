package com.juc.cf;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runAsyncNoExecutor();
        runAsync();

    }

    public static void runAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);
        // completableFuture.get()不调用get方法异步任务不会执行
        // pool-1-thread-1
        System.out.println(completableFuture.get());
    }

    public static void runAsyncNoExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // completableFuture.get()不调用get方法异步任务不会执行
        // ForkJoinPool.commonPool-worker-1
        System.out.println(completableFuture.get());
    }
}
