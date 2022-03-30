package com.luojia.interview.study.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class CountDownLatchDemo {
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(6);
		
		for (int i = 1; i <= 6; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName()+"\t 国，被灭");
				countDownLatch.countDown();
			}, CountryEnum.forEach_countryEnum(i).getRetMessage()).start();
		}
		
		countDownLatch.await();// 使得主线程（main）阻塞直到endLatch.countDown()为零才继续执行
		System.out.println(Thread.currentThread().getName()+"\t*******秦帝国一统华夏");
	}

}
