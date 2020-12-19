package com.romanticlei.redis.boot_redis01.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Redisson redisson;

    @Value("${server.port}")
    private String serverPort;

    public static String REDIS_LOCK = "romanticlei";

    @GetMapping("/buy_goods")
    public String buy_Goods() {

        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

        RLock redissonLock = redisson.getLock(REDIS_LOCK);
        redissonLock.lock();

        try {
            // 当前key不存在就设置key，返回true；当前key存在就不设置，返回false
            // Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);// 该命令就是redis中的setnx
            // stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);
            // 设置过期时间
            // Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);// 该命令就是redis中的setnx
            //
            // if (!flag) {
            //     return "抢占失败，请重试！";
            // }

            String result = stringRedisTemplate.opsForValue().get("goods:001");
            int goodNumber = result == null ? 0 : Integer.parseInt(result);

            if (goodNumber > 0) {
                int realNumber = goodNumber - 1;
                stringRedisTemplate.opsForValue().set("goods:001", String.valueOf(realNumber));
                System.out.println("成功买到商品，库存还剩下： " + realNumber + "件" + "\t 服务提供端口" + serverPort);
                stringRedisTemplate.delete(REDIS_LOCK);
                return "成功买到商品，库存还剩下： " + realNumber + "件" + "\t 服务提供端口" + serverPort;
            }

            return "商品已经售完" + "\t 服务提供端口" + serverPort;
        } finally {
            // // 判断加锁与解锁不是同一个客户端
            // if (value.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(REDIS_LOCK))) {
            //     // 若在此时，这把锁突然不是这个客户端的，则会误解锁
            //     stringRedisTemplate.delete(REDIS_LOCK);
            // }
            // while (true){
            //     stringRedisTemplate.watch(REDIS_LOCK);
            //     if (stringRedisTemplate.opsForValue().get(REDIS_LOCK).equalsIgnoreCase(value)){
            //         // 设置开启事务
            //         stringRedisTemplate.setEnableTransactionSupport(true);
            //         stringRedisTemplate.multi();
            //         stringRedisTemplate.delete(REDIS_LOCK);
            //         List<Object> list = stringRedisTemplate.exec();
            //         // 如果不为空，表示删除成功
            //         if (list == null){
            //             continue;
            //         }
            //     }
            //     stringRedisTemplate.unwatch();
            //     break;
            // }
            redissonLock.unlock();
        }
    }
}
