package com.romanticlei.redis.boot_redis01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GoodController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static String REDIS_LOCK = "romanticlei";

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/buy_goods")
    public String buy_Goods() {

        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        try {
            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(REDIS_LOCK, value);// 该命令就是redis中的setnx

            if (!flag) {
                return "抢占失败，请重试！";
            }

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
            stringRedisTemplate.delete(REDIS_LOCK);
        }
    }

}
