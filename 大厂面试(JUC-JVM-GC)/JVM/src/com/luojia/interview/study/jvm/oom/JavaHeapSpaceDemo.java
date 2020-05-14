package com.luojia.interview.study.jvm.oom;

import java.util.Random;

/**
 * OutOfMemoryError 是error不是exception
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class JavaHeapSpaceDemo {

	public static void main(String[] args) {
		String str = "learning";
		
		while(true) {
			str += str + new Random().nextInt(10000)+new Random().nextInt(50000);
			str.intern();
			// Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
		}
	}
	
}
