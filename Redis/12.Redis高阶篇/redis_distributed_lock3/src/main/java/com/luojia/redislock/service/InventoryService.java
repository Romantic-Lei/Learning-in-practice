package com.luojia.redislock.service;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class InventoryService {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${server.port}")
    private String port;

    private Lock lock = new ReentrantLock();

    /*public String sale() {
        String resMessgae = "";
        lock.lock();
        try {
            // 1 查询库存信息
            String result = stringRedisTemplate.opsForValue().get("inventory01");
            // 2 判断库存书否足够
            Integer inventoryNum = result == null ? 0 : Integer.parseInt(result);
            // 3 扣减库存，每次减少一个库存
            if (inventoryNum > 0) {
                stringRedisTemplate.opsForValue().set("inventory01", String.valueOf(--inventoryNum));
                resMessgae = "成功卖出一个商品，库存剩余：" + inventoryNum + "\t" + "，服务端口号：" + port;
                log.info(resMessgae);
            } else {
                resMessgae = "商品已售罄。" + "\t" + "，服务端口号：" + port;
                log.info(resMessgae);
            }

        } finally {
            lock.unlock();
        }

        return resMessgae;
    }*/

    public String sale() {
        String resMessgae = "";
        String key = "luojiaRedisLocak";
        String uuidValue = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();

        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue);
        // 抢不到的线程继续重试
        if (!flag) {
            // 线程休眠20毫秒，进行递归重试
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sale();
        } else {
            try {
                // 1 抢锁成功，查询库存信息
                String result = stringRedisTemplate.opsForValue().get("inventory01");
                // 2 判断库存书否足够
                Integer inventoryNum = result == null ? 0 : Integer.parseInt(result);
                // 3 扣减库存，每次减少一个库存
                if (inventoryNum > 0) {
                    stringRedisTemplate.opsForValue().set("inventory01", String.valueOf(--inventoryNum));
                    resMessgae = "成功卖出一个商品，库存剩余：" + inventoryNum + "\t" + "，服务端口号：" + port;
                    log.info(resMessgae);
                } else {
                    resMessgae = "商品已售罄。" + "\t" + "，服务端口号：" + port;
                    log.info(resMessgae);
                }
            } finally {
                stringRedisTemplate.delete(key);
            }
        }



        return resMessgae;
    }

}
