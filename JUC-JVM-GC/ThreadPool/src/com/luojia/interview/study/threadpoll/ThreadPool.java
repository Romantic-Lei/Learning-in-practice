package com.luojia.interview.study.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	
	public static void main(String[] args) {
		ExecutorService threadPool = new ThreadPoolExecutor(
				2, // 线程中常驻的核心线程数
				5, // 线程中同时容纳的最大线程数
				100, // 多余的空闲线程存活时间
				TimeUnit.SECONDS, // KeepAliveTime单位
				new LinkedBlockingQueue<>(3), // 等候区
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy());
		
		try {
			// 模拟6个顾客来办理业务，受理窗口只有5个
			for (int i = 1; i <= 6; i++) {
				final int temp = i;
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName()+"号窗口，"+"服务顾客"+temp);
					try {
						TimeUnit.SECONDS.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			threadPool.shutdown();
		}
		
	}

}
