package com.luojia.interview.study;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用（引用队列）
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class ReferenceQueueDemo {

	public static void main(String[] args) throws InterruptedException {
		Object obj = new Object();
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
		WeakReference<Object> weakReference = new WeakReference<>(obj, referenceQueue);
		System.out.println(obj);// java.lang.Object@7852e922
		System.out.println(weakReference.get());// java.lang.Object@7852e922
		System.out.println(referenceQueue.poll());// null
		System.out.println("=====================");
		
		obj = null;
		System.gc();
		TimeUnit.MILLISECONDS.sleep(500);
		
		System.out.println(obj);// null
		System.out.println(weakReference.get());// null
		System.out.println(referenceQueue.poll());// java.lang.ref.WeakReference@4e25154f
	}
	
}
