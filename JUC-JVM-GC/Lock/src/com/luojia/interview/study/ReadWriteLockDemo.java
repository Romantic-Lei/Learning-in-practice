package com.luojia.interview.study;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月9日
 * 多个线程同时读取一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源，就不该再有其他线程可以对该资源进行读或写
 * 即：
 * 	读-读能共存
 * 	读-写不能共存
 *	 写-写不能共存
 */
public class ReadWriteLockDemo {
	
	public static void main(String[] args) {
		MyCache myCache = new MyCache();
		
		for (int i = 1; i <= 5; i++) {
			int tempInt = i;
			new Thread(() -> {
				myCache.put(tempInt+"", tempInt);
			}, String.valueOf(i)).start();
		}
		for (int i = 1; i <= 5; i++) {
			int tempInt = i;
			new Thread(() -> {
				myCache.get(tempInt+"");
			}, String.valueOf(i)).start();
		}
	}
}

//资源类
class MyCache{
	private volatile Map<String, Object> map = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public void put(String key, Object value) {
		rwLock.writeLock().lock();
		System.out.println(Thread.currentThread().getName()+"\t正在写入"+key);
		try {
			// 模拟网络拥堵
			TimeUnit.MILLISECONDS.sleep(300);
			map.put(key, value);
			System.out.println(Thread.currentThread().getName()+"\t写入完成"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public void get(String key) {
		rwLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t正在读取");
			// 模拟网络拥堵
			TimeUnit.MILLISECONDS.sleep(300);
			Object res = map.get(key);
			System.out.println(Thread.currentThread().getName()+"\t读取完成"+res);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rwLock.readLock().unlock();
		}
	}
	
}
