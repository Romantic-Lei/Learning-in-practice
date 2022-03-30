package com.luojia.interview.study;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class SoftReferenceDemo {
	
	// 内存够用的时候保留，不够用就回收
	public static void softRef_Memory_Enough() {
		Object obj1 = new Object();// 这样定义的默认就是强引用
		SoftReference<Object> softReference = new SoftReference<>(obj1);
		System.out.println(obj1);// java.lang.Object@7852e922
		System.out.println(softReference.get());// java.lang.Object@7852e922
		
		obj1 = null;
		System.gc();
		
		System.out.println(obj1);// null
		System.out.println(softReference.get());//java.lang.Object@7852e922
	}
	
	// -Xms5m -Xmx5m -XX:+PrintGCDetails
	public static void softRef_Memory_NotEnough() {
		Object obj1 = new Object();// 这样定义的默认就是强引用
		SoftReference<Object> softReference = new SoftReference<>(obj1);
		System.out.println(obj1);// java.lang.Object@7852e922
		System.out.println(softReference.get());// java.lang.Object@7852e922
		
		obj1 = null;
		
		try {
			Byte[] bytes = new Byte[50*1024*1024];
		} catch (Exception e) {
			e.printStackTrace(); // java.lang.OutOfMemoryError: Java heap space
		}finally {
			System.out.println(obj1);// null
			System.out.println(softReference.get());// null
		}
	}
	
	public static void main(String[] args) {
//		softRef_Memory_Enough();
		
		softRef_Memory_NotEnough();
	}

}
