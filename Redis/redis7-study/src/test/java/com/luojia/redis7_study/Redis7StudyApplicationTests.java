package com.luojia.redis7_study;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Redis7StudyApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testGuava() {
        // 1 创建Guava 版本布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 100);
        // 2 判断指定的元素是否存在
        System.out.println(bloomFilter.mightContain(1));// false
        System.out.println(bloomFilter.mightContain(2));// false
        System.out.println();
        // 3 将元素新增进入布隆过滤器
        bloomFilter.put(1);
        bloomFilter.put(2);
        System.out.println(bloomFilter.mightContain(1));// true
        System.out.println(bloomFilter.mightContain(2));// true
    }

}
