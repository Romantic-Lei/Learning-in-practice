package com.luojia.interview.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Romantic-Lei
 * @create 2020年5月8日
 * 
 * CAS是什么？  ===> compareAndSet
 * 比较并交换
 */
public class CasDemo {
	
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		
		// main do thing
		System.out.println(atomicInteger.compareAndSet(5, 2020) + "\t current data"
				+ atomicInteger.get());
		
		System.out.println(atomicInteger.compareAndSet(5, 2020) + "\t current data"
				+ atomicInteger.get());
		
		atomicInteger.getAndIncrement();
	}

}