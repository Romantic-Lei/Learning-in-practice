package com.luojia.interview.study.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 第四种获得/使用java多线程的方式，线程池
 * 
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class MyThreadPoolDemo {

	public static void main(String[] args) {

		System.out.println(Runtime.getRuntime().availableProcessors());

		ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3), Executors.defaultThreadFactory(),
				// new ThreadPoolExecutor.AbortPolicy() // 当一个线程进来发现最大线程数和线程等待数都已占满直接报错
				// new ThreadPoolExecutor.CallerRunsPolicy()// “调用者运行”一种调节机制，该策略既不会抛弃任务
				// new ThreadPoolExecutor.DiscardOldestPolicy()// 抛弃队列中等待最久的，即只处理8（最大线程数+最多等待线程数）个线程
				new ThreadPoolExecutor.DiscardPolicy()// 抛弃最新来的线程,即只处理8个线程
		);
		try {
			// 模拟10个顾客来办理业务，受理窗口只有5个
			for (int i = 1; i <= 10; i++) {
				final int temp = i;
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "号窗口，" + "服务顾客" + temp);
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}

	}

	public static void testPoolInit() {
		// 一个连接池处理5个线程
		// ExecutorService threadPool = Executors.newFixedThreadPool(5);
		// 一个连接池只有一个线程
		// ExecutorService threadPool = Executors.newSingleThreadExecutor();
		// 一个连接池n个线程
		ExecutorService threadPool = Executors.newCachedThreadPool();

		// 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
		try {
			for (int i = 1; i <= 10; i++) {
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "\t 办理业务");
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}

}
