package com.luojia.interview.study.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列SychronousQueueDemo演示
 * SynchronousQueue是一个不存储元素的BlockingQueue。
 * 每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class SychronousQueueDemo {

	public static void main(String[] args) {
		BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
		
		new Thread(() -> {
			try {
				System.out.println(Thread.currentThread().getName()+"\t put 1");
				blockingQueue.put("1");
				System.out.println(Thread.currentThread().getName()+"\t put 2");
				blockingQueue.put("2");
				System.out.println(Thread.currentThread().getName()+"\t put 3");
				blockingQueue.put("3");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "AAA").start();
		
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName()+"\t "+blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "BBB").start();
	}
	
}
