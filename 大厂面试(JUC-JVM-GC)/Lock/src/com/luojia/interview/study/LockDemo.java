package com.luojia.interview.study;

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
		
	}
	
}

class Phone{
	public synchronized void sendSMS() throws Exception{
		System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
		sendEmail();
	}
	
	public synchronized void sendEmail() throws Exception{
		System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
	}
}
