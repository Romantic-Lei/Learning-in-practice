package com.luojia.interview.study.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReferenceDemo
 * @author Romantic-Lei
 * @create 2020年5月10日
 */
public class AtomicReferenceDemo {

	public static void main(String[] args) {
		User z3 = new User("z3", 22);
		User l3 = new User("l3", 25);

		AtomicReference<User> atomicReference = new AtomicReference<>();
		atomicReference.set(z3);

		System.out.println(atomicReference.compareAndSet(z3, l3) + "\t" + atomicReference.get().toString());
	}

}

class User {
	String username;
	int age;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public User(String username, int age) {
		super();
		this.username = username;
		this.age = age;
	}
}
