package com.luojia.interview.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class CyclicBarrierDemo {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
			System.out.println("执行业务完毕");
		});
		
		for (int i = 1; i <= 7; i++) {
			final int tempInt = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName()+"业务员编号："+tempInt);
				try {
					// 先到的被阻塞，等全部线程完成后，才能执行方法
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
	
}
