package com.luojia.redislock.mylock;

import cn.hutool.core.util.IdUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自研的分布式锁，实现了Lock接口
 */
public class RedisDistributedLock implements Lock {

    private StringRedisTemplate stringRedisTemplate;

    private String lockName; // KEYS[1]
    private String uuidValule; // ARGV[1]
    private long expireTime; // ARGV[2]

    // V7.1
    /*public RedisDistributedLock(StringRedisTemplate stringRedisTemplate, String lockName) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.uuidValule = IdUtil.simpleUUID() + ":" + Thread.currentThread().getId();
        this.expireTime = 50L;
    }*/

    // V7.2
    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate, String lockName, String uuid) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.uuidValule = uuid + ":" + Thread.currentThread().getId();
        this.expireTime = 30L;
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public boolean tryLock() {
        try {
            tryLock(-1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (-1 == time) {
            String script =
                    "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1 then " +
                        "redis.call('hincrby', KEYS[1], ARGV[1], 1) " +
                        "redis.call('expire', KEYS[1], ARGV[2]) " +
                        "return 1 " +
                    "else " +
                         "return 0 " +
                    "end";
            System.out.println("lock() lockName:" + lockName + "\t" + "uuidValue:" + uuidValule);

            // 加锁失败需要自旋一直获取锁 CAS思想
            while (!stringRedisTemplate.execute(
                    new DefaultRedisScript<>(script, Boolean.class),
                    Arrays.asList(lockName),
                    uuidValule,
                    String.valueOf(expireTime))) {
                // 休眠60毫秒再来重试
                try {TimeUnit.MILLISECONDS.sleep(60);} catch (InterruptedException e) {e.printStackTrace();}
            }
            // 新建一个后台扫描程序，来检查Key目前的ttl，是否到我们规定的剩余时间来实现锁续期
            resetExpire();
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        String script = "" +
                "if redis.call('hexists', KEYS[1], ARGV[1]) == 0 then " +
                "return nil " +
                "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0 then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        System.out.println("unlock() lockName:" + lockName + "\t" + "uuidValue:" + uuidValule);

        // LUA脚本由C语言编写，nil -> false; 0 -> false; 1 -> true;
        // 所以此处DefaultRedisScript构造函数返回值不能是Boolean，Boolean没有nil
        Long flag = stringRedisTemplate.execute(
                new DefaultRedisScript<>(script, Long.class),
                Arrays.asList(lockName),
                uuidValule);
        if (null == flag) {
            throw new RuntimeException("this lock does not exists.");
        }
    }

    private void resetExpire() {
        String script =
                "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then " +
                    "return redis.call('expire', KEYS[1], ARGV[2]) " +
                "else " +
                    "return 0 " +
                "end";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (stringRedisTemplate.execute(
                        new DefaultRedisScript<>(script, Boolean.class),
                        Arrays.asList(lockName),
                        uuidValule,
                        String.valueOf(expireTime))) {
                    // 续期成功，继续监听
                    System.out.println("resetExpire() lockName:" + lockName + "\t" + "uuidValue:" + uuidValule);
                    resetExpire();
                }
            }
        }, (this.expireTime * 1000) / 3);

    }

    // 下面两个暂时用不到，不用重写
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
