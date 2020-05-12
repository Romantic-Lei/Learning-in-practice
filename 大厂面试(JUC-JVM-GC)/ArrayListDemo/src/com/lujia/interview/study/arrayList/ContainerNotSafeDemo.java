package com.lujia.interview.study.arrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全问题
 * 
 * @author Romantic-Lei
 * @create 2020年5月9日
 */
public class ContainerNotSafeDemo {

	public static void main(String[] args) {

		Map<String, String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new ConcurrentHashMap<>();//new HashMap<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
				System.out.println(map);
			}, String.valueOf(i)).start();
		}

	}

	public static void setNotSafe() {
		// HashSet的底层就是HashMap，但是我们HashSet使用add时只有一个值的原因是：
		// 我们存储的这个值是HashMap的key，value是一个常量，不用我们关心。
		Set<String> set = new CopyOnWriteArraySet<>();// Collections.synchronizedSet(new HashSet<>()); //new
														// HashSet<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(set);
			}, String.valueOf(i)).start();
		}
	}

	public static void listNotSafe() {
		// List<String> list = Arrays.asList("a", "b", "c");
		// list.forEach(System.out::println);

		// Collections.synchronizedList(new ArrayList<>()); //new Vector<>();//new
		// ArrayList<>();
		List<String> list = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			}, String.valueOf(i)).start();

			/**
			 * 1.故障现象 java.util.ConcurrentModificationException
			 * 
			 * 2.导致原因 并发争抢修改导致发生异常
			 * 
			 * 3.解决方案 3.1 使用new Vector<>(); 3.2 使用Collections.synchronizedList(new
			 * ArrayList<>()); 3.3使用new CopyOnWriteArrayList<>();
			 * CopyOnWriteArrayList：写时复制，主要是一种读写分离的思想
			 * 写时复制，CopyOnWrite容器即写时复制的容器，往一个容器中添加元素的时候，不直接往当前容器Object[]添加，
			 * 而是先将Object[]进行copy，复制出一个新的容器object[] newElements，然后新的容器Object[]
			 * newElements里添加原始数据， 添加元素完后，在将原容器的引用指向新的容器
			 * setArray(newElements)；这样做的好处是可以对copyOnWrite容器进行并发的度，
			 * 而不需要加锁，因为当前容器不需要添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
			 * 就是写的时候，把ArrayList扩容一个出来，然后把值填写上去，在通知其他的线程，ArrayList的引用指向扩容后的地址
			 * 
			 * 
			 * 4.优化建议
			 */

		}
	}
}
