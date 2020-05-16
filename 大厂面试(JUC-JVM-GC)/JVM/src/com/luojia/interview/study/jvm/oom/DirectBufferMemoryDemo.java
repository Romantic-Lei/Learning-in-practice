package com.luojia.interview.study.jvm.oom;

import java.nio.ByteBuffer;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * 只给内存分配5M的空间，但是使用的时候，需要6M,运行会报内存溢出
 * @author Romantic-Lei
 * @create 2020年5月15日
 */
public class DirectBufferMemoryDemo {

	public static void main(String[] args) {
		
		// ByteBuffer.allocateDirect(capability)方式是分配OS本地内存，不属于GC管辖范围
		// ，由于不需要内存拷贝，所以速度相对较快。
		// Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
		ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);
		
	}
	
}
