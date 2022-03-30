package com.luojia.interview.study.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 多个线程抢占多个资源（可能资源数少于线程数）
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class SemaphoreDemo {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(3);// 模拟3个车位
		
		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				try {
					semaphore.acquire();// 开始抢占
					System.out.println(Thread.currentThread().getName()+"\t抢到车位");
					
					TimeUnit.SECONDS.sleep(3);
					System.out.println(Thread.currentThread().getName()+"\t停车3秒后离开车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					semaphore.release();// 释放车位
				}
			}, String.valueOf(i)).start();
		}
	}
	
}
