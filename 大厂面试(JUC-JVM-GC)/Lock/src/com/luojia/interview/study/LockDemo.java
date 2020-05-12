package com.luojia.interview.study;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

	public static void main(String[] args) {
		Phone phone = new Phone();
		
		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t1").start();
		
		new Thread(() -> {
			try {
				phone.sendSMS();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t2").start();
		
		
		System.out.println("---------------------------");
		Thread t3 = new Thread(phone, "t3");
		Thread t4 = new Thread(phone, "t4");
		t3.start();
		t4.start();
		
	}
	
}

class Phone implements Runnable{
	public synchronized void sendSMS() throws Exception{
		System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
		sendEmail();
	}
	
	public synchronized void sendEmail() throws Exception{
		System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
	}

	Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		get();
	}
	
	public void get() {
		// 可以同时加多把锁，但是一定是加锁次数和解锁次数相同才可以，否则死锁
		lock.lock();
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoked get()");
			set();
		} finally {
			lock.unlock();
			lock.unlock();
		}
	}
	
	public void set() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "\t invoked set()");
		} finally {
			lock.unlock();
		}
	}
	
}
