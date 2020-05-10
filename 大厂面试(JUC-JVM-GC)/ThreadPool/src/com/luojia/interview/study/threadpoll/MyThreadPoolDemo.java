package com.luojia.interview.study.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 第四种获得/使用java多线程的方式，线程池
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class MyThreadPoolDemo {

	public static void main(String[] args) {
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
					System.out.println(Thread.currentThread().getName()+"\t 办理业务");
				});
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}
	
}
