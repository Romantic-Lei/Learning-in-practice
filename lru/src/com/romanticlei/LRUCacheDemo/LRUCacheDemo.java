package com.romanticlei.LRUCacheDemo;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheDemo<K, V> extends LinkedHashMap<K, V> {

    private int capacity; // 缓存坑位

    public LRUCacheDemo(int capacity) {
        // super(capacity, 0.75F, true);
        super(capacity, 0.75F, false);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheDemo lruCacheDemo = new LRUCacheDemo(3);

        lruCacheDemo.put(1, "a");
        lruCacheDemo.put(2, "b");
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());

        /**
         * super(capacity, 0.75F, true); --> 最近最少使用先出
         * [1, 2, 3]
         * [2, 3, 5]
         * [2, 5, 3]
         * [2, 5, 3]
         * [2, 5, 3]
         * [5, 3, 6]
         *
         * super(capacity, 0.75F, false); --> 先进先出
         * [1, 2, 3]
         * [2, 3, 5]
         * [2, 3, 5]
         * [2, 3, 5]
         * [2, 3, 5]
         * [3, 5, 6]
         *
         */
        lruCacheDemo.put(5, "f");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(6, "g");
        System.out.println(lruCacheDemo.keySet());
    
    }
}
