package com.luojia.redis7_study.controller;

import cn.hutool.core.util.IdUtil;
import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class RedPackageController {

    public static final String RED_PACKAGE_KEY = "redpackage:";
    public static final String RED_PACKAGE_CONSUME_KEY = "redpackage:consume";

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/send")
    public String sendRedPackage(int totalMoney, int redpackageNumber) {
        // 1 拆红包将总金额totalMoney拆分成redpackageNumber个子红包
        Integer[] splitRedPackages = splitRedPackageAlgorithm(totalMoney, redpackageNumber);
        // 2 发红包并保存进list结构里面且设置过期时间
        String key = RED_PACKAGE_KEY + IdUtil.simpleUUID();
        redisTemplate.opsForList().leftPushAll(key, splitRedPackages);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);

        return key + "\t" + Ints.asList(Arrays.stream(splitRedPackages).mapToInt(Integer::valueOf).toArray());
    }

    @RequestMapping(value = "/rob")
    public String robRedPackage(String redpackageKey, String userId) {
        // 验证某个用户是否抢过红包，不可以多抢
        Object redPackage = redisTemplate.opsForHash().get(RED_PACKAGE_CONSUME_KEY + redpackageKey, userId);
        if (redPackage == null) {
            // 红包没有抢完才能让用户接着抢
            Object partRedPackage = redisTemplate.opsForList().leftPop(RED_PACKAGE_KEY + redpackageKey);
            if(partRedPackage != null) {
                redisTemplate.opsForHash().put(RED_PACKAGE_CONSUME_KEY+redpackageKey, userId, partRedPackage);
                System.out.println("用户：" + userId + "\t 抢到了" + partRedPackage + "");
                // TODO 后续异步操作或者回滚操作
                return String.valueOf(partRedPackage);
            }
            // 抢完了
            return "errorCode:-1, 红包抢完了";
        }
        // 抢过了，不能抢多次
        return "errorCode:-2," + userId + "\t已经抢过了";

    }

    // 拆分红包算法 --》 二倍均值算法
    private Integer[] splitRedPackageAlgorithm(int totalMoney, int redpackageNumber) {
        Integer[] redpackageNumbers = new Integer[redpackageNumber];
        // 已经被抢夺的红包金额
        int useMoney = 0;
        for (int i = 0; i < redpackageNumber; i++) {
            if (i == redpackageNumber - 1) {
                redpackageNumbers[i] = totalMoney - useMoney;
            } else {
                // 二倍均值算法，每次拆分后塞进子红包的金额
                // 金额 = 随机区间(0，(剩余红包金额M ÷ 剩余人数N ) * 2)
                int avgMoney = ((totalMoney - useMoney) / (redpackageNumber - i)) * 2;
                redpackageNumbers[i] = 1 + new Random().nextInt(avgMoney - 1);
                useMoney = useMoney + redpackageNumbers[i];
            }
        }
        return redpackageNumbers;
    }
}
