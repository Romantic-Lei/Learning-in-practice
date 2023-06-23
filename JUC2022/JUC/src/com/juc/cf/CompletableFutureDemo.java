package com.juc.cf;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // runAsyncNoExecutor();
        // runAsync();
        // supplyAsyncNoExecutor();
        // supplyAsync();
        supplyAsync1();
    }

    public static void supplyAsync1() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            // pool-1-thread-1
            System.out.println(Thread.currentThread().getName() + "come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            if (result > 5) {
                int i = 10 / 0;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }, executorService).whenComplete((v, e) -> {
            if (null == e) {
                System.out.println("------计算完成，未发生异常，结果为：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("系统发生异常" + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println("main主线程执行自己的其他逻辑");
        executorService.shutdown();
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
        System.out.println("查看是否阻塞了后面的逻辑");
        executorService.shutdown();
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
