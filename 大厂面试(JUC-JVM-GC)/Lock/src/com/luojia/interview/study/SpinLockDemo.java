package com.luojia.interview.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月9日 实现一个自旋锁 自旋锁好处：循环比较获取直到成功为止，没有类似wait的阻塞
 * 
 *         通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后发现
 *         当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁B抢到为止
 */
public class SpinLockDemo {

	// 原子引用线程
	AtomicReference<Thread> atomicReference = new AtomicReference<>();

	public void myLock() {
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName() + "\t come in");

		while (!atomicReference.compareAndSet(null, thread)) {
			// System.out.println("阻塞");
		}
	}

	public void myUnlock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName() + "\t invoked myUnlock()");
	}

	public static void main(String[] args) {
		SpinLockDemo spinLockDemo = new SpinLockDemo();

		new Thread(() -> {
			try {
				spinLockDemo.myLock();
				TimeUnit.SECONDS.sleep(4);
			} catch (Exception e) {
				e.printStackTrace();
			}

			spinLockDemo.myUnlock();
		}, "AA").start();

		// 保证A线程先进入
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			spinLockDemo.myLock();
			spinLockDemo.myUnlock();
		}, "BB").start();

	}

}
