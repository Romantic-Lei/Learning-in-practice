package com.luojia.interview.study.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月10日 ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序
 *         LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量通常要高于ArrayBlockingQueue
 *         synchronousQueue:一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态
 * 
 *         1 队列
 * 
 *         2 阻塞队列 
 *         2.1 阻塞队列有没有好的一面
 * 
 *         2.2 不得不阻塞，你如何管理
 * 
 */
public class BlockingQueueDemo {

	public static void main(String[] args) throws InterruptedException {
		// 超时
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		
		// 存储数据，超时时间，时间单位
		System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS)); // true
		System.out.println(blockingQueue.offer("b", 2, TimeUnit.SECONDS)); // true
		System.out.println(blockingQueue.offer("c", 2, TimeUnit.SECONDS)); // true
		System.out.println(blockingQueue.offer("d", 2, TimeUnit.SECONDS)); // false, 队列装不下，会触发超时机制，2秒之后结束请求
		
		System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS)); // a
		System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS)); // b
		System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS)); // c
		System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS)); // 阻塞2秒后输出null
		
	}
	
	// 阻塞
	public static void queueBlocking() throws InterruptedException {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		blockingQueue.put("a");
		blockingQueue.put("b");
		blockingQueue.put("c");
		// 队列装不下之后会被一直阻塞，下面代码无法执行
		blockingQueue.put("x");
		
		blockingQueue.take();
		blockingQueue.take();
		blockingQueue.take();
	}
	
	// 特殊值
	public static void queueBoolean() {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		System.out.println(blockingQueue.offer("a"));
		System.out.println(blockingQueue.offer("b"));
		System.out.println(blockingQueue.offer("c"));
		// 装不下不会直接抛异常而是返回false
		System.out.println(blockingQueue.offer("X"));// false
		// 检查队首元素
		System.out.println(blockingQueue.peek()); // a
		// 弹出队首元素
		System.out.println(blockingQueue.poll()); // a
		System.out.println(blockingQueue.poll()); // b
		System.out.println(blockingQueue.poll()); // c
		System.out.println(blockingQueue.poll()); // null
		
		System.out.println(blockingQueue.peek()); // null
	}

	// 抛出异常
	public static void queueEx() {
		// 队列满，再添加元素直接报错,队列空，在取报错
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
		System.out.println(blockingQueue.add("a"));
		System.out.println(blockingQueue.add("b"));
		System.out.println(blockingQueue.add("c"));

		// 检查，输出队列的排头
		System.out.println(blockingQueue.element()); // a

		// java.lang.IllegalStateException
		// System.out.println(blockingQueue.add("x"));
		// 弹出一个数据
		System.out.println(blockingQueue.remove()); // a
		System.out.println(blockingQueue.remove()); // b
		System.out.println(blockingQueue.remove()); // c

		// java.util.NoSuchElementException
		// System.out.println(blockingQueue.remove());

	}

}
