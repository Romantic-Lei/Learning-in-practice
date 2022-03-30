package com.luojia.interview.study;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class WeakReferenceDemo {

	
	public static void main(String[] args) {
		Object obj = new Object();
		WeakReference<Object> weakReference = new WeakReference<>(obj);
		
		System.out.println(obj);// java.lang.Object@7852e922
		System.out.println(weakReference.get());// java.lang.Object@7852e922
		
		obj = null;
		System.gc();
		
		System.out.println(obj);// null
		System.out.println(weakReference.get());// null
		
	}
}
