package com.luojia.interview.study.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class ProdConsumer_BlockQueueDemo {

	public static void main(String[] args) {
		MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
		
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
			try {
				myResource.myProd();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Prod").start();
		
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
			try {
				myResource.myConsumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "Consumer").start();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("5秒时间到");
		myResource.stop();
	}
	
}

class MyResource{
	private volatile boolean FLAG = true;// 默认开启，进行生产+消费
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	BlockingQueue<String> blockingQueue = null;

	public MyResource(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	public void myProd() throws InterruptedException {
		String data = null;
		boolean retValue;
		while(FLAG) {
			data = atomicInteger.incrementAndGet() + "";
			retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
			
			if(retValue) {
				System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
			} else {
				System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName()+" 为false，生产动作结束");
	}
	
	public void myConsumer() throws InterruptedException {
		String result = null;
		while(FLAG) {
			result = blockingQueue.poll(2, TimeUnit.SECONDS);
			
			if(null == result || result.equalsIgnoreCase("")) {
				FLAG = false;
				System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到消息，消费退出");
				return ;
			}
			System.out.println(Thread.currentThread().getName()+"\t 消费队列"+result+"成功");
		}
	}
	
	public void stop() {
		this.FLAG = false;
	}
}
