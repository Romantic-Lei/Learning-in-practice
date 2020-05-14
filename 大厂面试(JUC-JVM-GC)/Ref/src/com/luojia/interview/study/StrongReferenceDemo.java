package com.luojia.interview.study;

/**
 * 强引用
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class StrongReferenceDemo {
	
	public static void main(String[] args) {
		Object obj1 = new Object();// 这样定义的默认就是强引用
		Object obj2 = obj1;// obj2引用赋值
		obj1 = null;// 置空
		System.gc();
		System.out.println(obj1);// null
		System.out.println(obj2);// java.lang.Object@7852e922
	}

}
