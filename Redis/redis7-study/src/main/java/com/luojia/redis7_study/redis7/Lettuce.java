package com.luojia.redis7_study.redis7;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class Lettuce {
    public static void main(String[] args) {
        // 1.使用构建器链式编程来builder我们RedisURI
        RedisURI uri = RedisURI.builder().redis("127.0.0.1")
                .withPort(6379)
                .withAuthentication("default", "123456")
                .build();
        // 2.创建连接客户端
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection connect = redisClient.connect();
        // 3.通过connect创建操作的command
        RedisCommands commands = connect.sync();

        List keys = commands.keys("*");
        System.out.println(keys);
        // string
        commands.set("k6", "v6");
        System.out.println("********" + commands.get("k6"));


        // 4.关闭释放资源
        connect.close();
        redisClient.shutdown();

    }
}
