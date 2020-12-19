package com.romanticlei.redis.boot_redis02.controller;

import com.romanticlei.redis.boot_redis02.util.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Redisson redisson;

    public static String REDIS_LOCK = "romanticlei";

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/buy_goods")
    public String buy_Goods() throws Exception {

        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();

        RLock redissonLock = redisson.getLock(REDIS_LOCK);
        redissonLock.lock();
        try {
            // 当前key不存在就设置key，返回true；当前key存在就不设置，返回false
            // Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);// 该命令就是redis中的setnx
            // stringRedisTemplate.expire(REDIS_LOCK, 10L, TimeUnit.SECONDS);

            // Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);// 该命令就是redis中的setnx
            //
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
            // Jedis jedis = RedisUtils.getJedis();
            //
            // String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
            //         "then\n" +
            //         "    return redis.call(\"del\",KEYS[1])\n" +
            //         "else\n" +
            //         "    return 0\n" +
            //         "end";
            //
            // try {
            //     Object obj = jedis.eval(script, Collections.singletonList(REDIS_LOCK), Collections.singletonList(value));
            //     if ("1".equals(obj.toString())){
            //         System.out.println("--------del redis lock ok");
            //     } else {
            //         System.out.println("--------del redis lock error");
            //     }
            // }finally {
            //     if (null != jedis){
            //         jedis.close();
            //     }
            // }
            redissonLock.unlock();
        }
    }
}
