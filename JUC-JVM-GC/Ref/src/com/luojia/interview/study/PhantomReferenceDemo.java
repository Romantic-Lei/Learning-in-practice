package com.luojia.interview.study;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 
 * @author Romantic-Lei
 * @create 2020年5月14日
 * java提供了4种引用类型，在垃圾回收的时候，都有自己各自的特点
 * ReferenceQueue是用来配合引用工作的，没有ReferenceQueue一样可以运行
 * 
 * 创建引用的时候可以指定关联的队列，当GC释放对象的时候，会将引用加入到引用队列，
 * 如果程序发现某个引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
 * 这相当于是一种通信机制
 * 
 * 当关联的引用队列中有数据的时候，意味着引用指向的堆内存中的对象被回收，通过这种方式，JVM允许我们在对象被销毁后，
 * 做一些我们自己想做的事情。
 */
public class PhantomReferenceDemo {

	public static void main(String[] args) throws InterruptedException {
		Object obj = new Object();
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
		PhantomReference<Object> phantomReference = new PhantomReference<Object>(obj, referenceQueue);
				
		System.out.println(obj);// java.lang.Object@7852e922
		System.out.println(phantomReference.get());// null
		System.out.println(referenceQueue.poll());// null
		
		System.out.println("===========================");
		
		obj = null;
		System.gc();
		Thread.sleep(500);
		
		System.out.println(obj);// null
		System.out.println(phantomReference.get());// null
		System.out.println(referenceQueue.poll());// java.lang.ref.PhantomReference@4e25154f
	}
	
}
