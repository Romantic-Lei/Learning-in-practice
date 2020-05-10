package com.luojia.interview.study.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决，AtomicStampedReference
 * 
 * @author Romantic-Lei
 * @create 2020年5月9日
 */
public class ABADemo {

	static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
	static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 1);

	public static void main(String[] args) {
		
		System.out.println("===========以下是ABA问题的产生============");
		new Thread(() -> {
			atomicReference.compareAndSet(100, 101);
			atomicReference.compareAndSet(101, 100);
		}, "t1").start();

		new Thread(() -> {
			// 线程t2暂停1秒钟，保证线程t1完成一次ABA操作
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(atomicReference.compareAndSet(100, 2020) + "\t" + atomicReference.get());
		}, "t2").start();

		// 暂停一会线程，保证上面两个线程完成
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("===========以下是ABA问题的解决============");
		
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "第一次版本号" + stamp);
			
			// 暂停线程1秒 t3线程
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName() + "第二次版本号" + atomicStampedReference.getStamp());
			
			atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			System.out.println(Thread.currentThread().getName() + "第三次版本号" + atomicStampedReference.getStamp());
		}, "t3").start();
		
		new Thread(() -> {
			int stamp = atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName() + "第一次版本号" + stamp);
			
			// 暂停线程3秒 t4线程, 保证线程t3完成一次ABA操作
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean result = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp+1);
			
			System.out.println(Thread.currentThread().getName() + "\t修改成功否 " + result + "\t当前最新版本号" + atomicStampedReference.getStamp());
			
			System.out.println(Thread.currentThread().getName() + "\t当前实际最新值：" + atomicStampedReference.getReference());
		}, "t4").start();
		
	}

}