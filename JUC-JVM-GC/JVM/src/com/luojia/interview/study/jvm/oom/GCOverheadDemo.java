package com.luojia.interview.study.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 * --Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * @author Romantic-Lei
 * @create 2020年5月15日
 */
public class GCOverheadDemo {

	public static void main(String[] args) {
		int i = 0;
		List<String> list = new ArrayList<>();
		
		try {
			while(true) {
				list.add(String.valueOf(++i).intern());
			}
		} catch (Throwable e) {
			System.out.println("*************i = " + i);
			// java.lang.OutOfMemoryError: GC overhead limit exceeded
			e.printStackTrace();
		}
	}
}
