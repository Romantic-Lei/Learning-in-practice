package com.luojia.interview.study;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 弱引用
 * @author Romantic-Lei
 * @create 2020年5月14日
 */
public class WeakHashMapDemo {

	public static void main(String[] args) {
		myHashMap();
		System.out.println("===========================");
		myWeakHashMap();
	}

	public static void myHashMap() {
		HashMap<Integer, String> map = new HashMap<>();
		Integer key = new Integer(1);
		String value = "hashMap";

		map.put(key, value);
		System.out.println(map);// {1=hashMap}

		key = null;
		System.out.println(map);// {1=hashMap}
		System.gc();
		System.out.println(map);// {1=hashMap}
	}

	public static void myWeakHashMap() {
		WeakHashMap<Integer, String> map = new WeakHashMap<>();
		Integer key = new Integer(2);
		String value = "hashMap";

		map.put(key, value);
		System.out.println(map);// {2=hashMap}

		key = null;
		System.out.println(map);// {2=hashMap}
		System.gc();
		System.out.println(map);// {}
	}
}
