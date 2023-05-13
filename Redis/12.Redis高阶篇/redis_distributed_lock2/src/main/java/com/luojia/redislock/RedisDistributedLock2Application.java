package com.luojia.redislock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisDistributedLock2Application {

    public static void main(String[] args) {
        SpringApplication.run(RedisDistributedLock2Application.class, args);
    }

}
