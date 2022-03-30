package com.luojia.interview.study;

import java.util.Random;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月16日
 * 1
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC
 * 
 * 2
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC
 * 
 * 3
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
 * 
 * 4
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 */
public class GCDemo {

	public static void main(String[] args) {
		System.out.println("*************GCDemo hello");
		try {
			String str = "Hello World";
			while(true) {
				str += str + new Random().nextInt(10000);
				str.intern();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
