package com.juc.cf;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // runAsyncNoExecutor();
        // runAsync();
        // supplyAsyncNoExecutor();
        supplyAsync();
    }

    public static void supplyAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            // pool-1-thread-1
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsyncNoExecutor";
        }, executorService);
        // hello supplyAsyncNoExecutor
        System.out.println(completableFuture.get());
    }

    public static void supplyAsyncNoExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            // ForkJoinPool.commonPool-worker-1
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsyncNoExecutor";
        });
        // hello supplyAsyncNoExecutor
        System.out.println(completableFuture.get());
    }

    public static void runAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            // pool-1-thread-1
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, executorService);
        // null
        System.out.println(completableFuture.get());
        executorService.shutdown();
    }

    public static void runAsyncNoExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            // ForkJoinPool.commonPool-worker-1
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // null
        System.out.println(completableFuture.get());
    }
}
