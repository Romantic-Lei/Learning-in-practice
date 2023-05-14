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

    // v2.0 单机版锁，不满足分布式微服务加锁要求，会出现超卖
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

    /* v3.1 递归重试，容易导致stackoverflowerror，所以不太推荐，另外高并发唤醒后，推荐用while判断而不是if
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
    }*/

    /*v3.2 部署了微服务的Java程序机器挂了，代码层面根本没有走到finally这块，
    没办法保证解锁(无过期时间该key一直存在)，这个key没有被删除，
    需要加入一个过期时间限定key
    public String sale() {
        String resMessgae = "";
        String key = "luojiaRedisLocak";
        String uuidValue = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();

        // 不用递归了，高并发容易出错，我们用自旋代替递归方法重试调用；也不用if，用while代替
        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue)) {
            // 线程休眠20毫秒，进行递归重试
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
        }

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
        return resMessgae;
    }*/

    // v4.2
    /*实际业务中，如果处理时间超过了设置的key的过期时间，那删除key的时候岂不是张冠李戴，删除了别人的锁
    public String sale() {
        String resMessgae = "";
        String key = "luojiaRedisLocak";
        String uuidValue = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();

        // 不用递归了，高并发容易出错，我们用自旋代替递归方法重试调用；也不用if，用while代替
        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue, 30L, TimeUnit.SECONDS)) {
            // 线程休眠20毫秒，进行递归重试
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
        }

        // 设置过期时间，但是这里设置的话不具备原子性
        // stringRedisTemplate.expire(key, 30L, TimeUnit.SECONDS);
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
        return resMessgae;
    }*/

    // v5.0 版本，存在问题就是finally中判断和del不是原子性的，需要使用Lua脚本
    /*public String sale() {
        String resMessgae = "";
        String key = "luojiaRedisLocak";
        String uuidValue = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();

        // 不用递归了，高并发容易出错，我们用自旋代替递归方法重试调用；也不用if，用while代替
        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue, 30L, TimeUnit.SECONDS)) {
            // 线程休眠20毫秒，进行递归重试
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
        }

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
            // v5.0 改进点，判断加锁与解锁是不同客户端，自己只能删除自己的锁，不误删别人的锁
            if (uuidValue.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(key))) {
                stringRedisTemplate.delete(key);
            }
        }
        return resMessgae;
    }*/

    // v6.0
    public String sale() {
        String resMessgae = "";
        String key = "luojiaRedisLocak";
        String uuidValue = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();

        // 不用递归了，高并发容易出错，我们用自旋代替递归方法重试调用；也不用if，用while代替
        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue, 30L, TimeUnit.SECONDS)) {
            // 线程休眠20毫秒，进行递归重试
            try {TimeUnit.MILLISECONDS.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
        }

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
            // v5.0 改进点，判断加锁与解锁是不同客户端，自己只能删除自己的锁，不误删别人的锁
            if (uuidValue.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(key))) {
                stringRedisTemplate.delete(key);
            }
        }
        return resMessgae;
    }

}
