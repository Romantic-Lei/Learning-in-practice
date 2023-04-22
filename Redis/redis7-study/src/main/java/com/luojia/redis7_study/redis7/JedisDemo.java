package com.luojia.redis7_study.redis7;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo {

    public static void main(String[] args) {

        // 1. connection获得，通过指定IP和端口厚爱
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 指定访问服务器的密码
        jedis.auth("123456");
        // 3. 获得了jedis客户端，可以像jdbc一样，访问我们的redis
        System.out.println(jedis.ping());// 返回 PONG 是正常

        // keys
        Set<String> keys = jedis.keys("*");
        System.out.println("keys" + keys);// keys[k1, k2, k3]

        // string
        String set = jedis.set("k5", "v5");
        System.out.println("set : " + set);// OK
        System.out.println("jedis string : " + jedis.get("k5"));// jedis string : v5

        // list
        jedis.lpush("list", "1", "2");
        List<String> list = jedis.lrange("list", 0, -1);
        for (String str: list) {
            System.out.println(str);// 2 1
        }

    }
}
