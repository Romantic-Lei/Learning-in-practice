package com.luojia.redis7_study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckUtils {

    @Autowired
    private RedisTemplate redisTemplate;
    
    public boolean checkWithBloomFilter(String checkItem, String key) {
        int hashValue = Math.abs(key.hashCode());
        long index = (long) (hashValue % Math.pow(2, 32));
        Boolean exitOk = redisTemplate.opsForValue().getBit(checkItem, index);
        log.info("---> key：{}对应坑位下标index：{}是否存在：{}", key, index, exitOk);

        return exitOk;
    }
}
