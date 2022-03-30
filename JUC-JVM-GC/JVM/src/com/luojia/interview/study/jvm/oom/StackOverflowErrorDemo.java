package com.luojia.interview.study.jvm.oom;

/**
 * StackOverflowError是Error不是exception
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class StackOverflowErrorDemo {

	public static void main(String[] args) {
		StackOverflowError();
	}

	public static void StackOverflowError() {
		StackOverflowError();// StackOverflowError
	}
	
}
