package com.luojia.demo.LiveRedis.service;

import com.luojia.demo.LiveRedis.entity.Constants;
import com.luojia.demo.LiveRedis.entity.Content;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TaskService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 规模直播间的数据
     */
    @PostConstruct
    public void init() {
        log.info("启动初始化，淘宝直播弹幕 case开始...");

        System.out.println();

        // 1 微服务启动一个线程，模拟直播间各个观众发言
        new Thread(() -> {
            AtomicInteger atomicInteger = new AtomicInteger();
            while (true) {
                // 2 模拟观众各种发言，5秒一批数据，自己模拟造一批发言数据到100后break
                if (atomicInteger.get() == 100) break;
                // 3 模拟直播间100房间号的弹幕数据，拼接Redis的key room:100
                String key = Constants.ROOM_KEY+100;
                Random random = new Random();

                for (int i = 1; i <= 5; i++) {
                    Content content = new Content();
                    content.setId(System.currentTimeMillis());

                    int id = random.nextInt(1000) + 1;
                    content.setUserId(id);

                    int temp = random.nextInt(100) + 1;
                    content.setContent("发表弹幕：" + temp+"\t"+ UUID.randomUUID());

                    long time = System.currentTimeMillis()/1000;
                    // 4 对应的Redis命令 zadd room:100 time content
                    redisTemplate.opsForZSet().add(key, content, time);
                    log.info("模拟直播间100房间号的发言弹幕数据：{}", content);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // 模拟观众发言，5秒一批数据，到100自动退出
                atomicInteger.getAndIncrement();
                System.out.println("--------每隔5秒钟，拉取一次最新的聊天记录");
            }
        }, "init_Live_Data").start();
    }
}
