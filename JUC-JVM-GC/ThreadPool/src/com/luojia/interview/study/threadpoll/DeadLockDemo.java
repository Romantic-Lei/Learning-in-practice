package com.luojia.interview.study.threadpoll;

import java.util.concurrent.TimeUnit;

/**
 * 死锁是指两个或两个以上的进程在执行中，
 * 因争夺资源造成的一种互相等待的现象，
 * 若无外力干涉那他们都将无法推进下去
 * @author Romantic-Lei
 * @create 2020年5月11日
 */
public class DeadLockDemo {

	public static void main(String[] args) {
		String lockA = "lockA";
		String lockB = "lockB";
		
		new Thread(new HoldLockThread(lockA, lockB), "ThreadAAA").start();
		new Thread(new HoldLockThread(lockB, lockA), "ThreadBBB").start();
		
	}
	
}

class HoldLockThread implements Runnable{

	private String LockA;
	private String LockB;
	
	public HoldLockThread(String lockA, String lockB) {
		this.LockA = lockA;
		this.LockB = lockB;
	}

	@Override
	public void run() {
		synchronized (LockA) {
			System.out.println(Thread.currentThread().getName()+"\t自己持有:"+LockA+"\t尝试获得"+LockB);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		synchronized (LockB) {
			System.out.println(Thread.currentThread().getName()+"\t自己持有:"+LockB+"\t尝试获得"+LockA);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
