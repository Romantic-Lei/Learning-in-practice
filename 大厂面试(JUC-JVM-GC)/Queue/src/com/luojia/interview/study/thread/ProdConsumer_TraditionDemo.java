package com.luojia.interview.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版生产者消费者
 * 题：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来五轮
 * 
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class ProdConsumer_TraditionDemo {

	public static void main(String[] args) {
		ShareData shareData = new ShareData();
		
		new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				try {
					shareData.increment();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "increment").start();
		
		new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				try {
					shareData.decrement();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "decrement").start();
	}

}

class ShareData {
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void increment() throws InterruptedException {
		lock.lock();
		try {
			// 1 判断
			while (number != 0) {
				// 等待，不能生产
				condition.await();
			}
			
			// 2干活
			number++;
			System.out.println(Thread.currentThread().getName() + "\t" + number);
			// 3通知唤醒
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public void decrement() throws InterruptedException {
		lock.lock();
		try {
			// 1 判断
			while (number == 0) {
				// 等待，不能获取
				condition.await();
			}
			
			// 2干活
			number--;
			System.out.println(Thread.currentThread().getName() + "\t" + number);
			// 3通知唤醒
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}
