package com.luojia.redis7_study.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class GuavaBloomFilterService {

    public static final int _1w = 10000;
    public static final int SIZE = 100 * _1w;
    // 误判率，它越小误判的个数也就越少
    public static double fpp = 0.03;
    // 创建Guava 版本布隆过滤器
    BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), SIZE, fpp);

    public void guavaBloomFilterService() {
        // 1 先让bloomFilter加入100w白名单数据
        for (int i = 0; i < SIZE; i++) {
            bloomFilter.put(i);
        }
        // 2 故意去10w不在合法范围内的数据，来进行误判率演示
        ArrayList<Integer> list = new ArrayList<>(10 * _1w);
        // 3 验证
        for (int i = SIZE; i < SIZE + (10 * _1w); i++) {
            if(bloomFilter.mightContain(i)) {
                log.info("被误判了：{}", i);
                list.add(i);
            }
        }

        log.info("误判的总数量：{}", list.size());
    }
}
