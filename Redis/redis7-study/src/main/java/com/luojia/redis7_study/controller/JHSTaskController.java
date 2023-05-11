package com.luojia.redis7_study.controller;

import com.luojia.redis7_study.entities.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "模拟聚划算商品上下架")
@RestController
@Slf4j
public class JHSTaskController {

    public static final String JHS_KEY = "jhs";
    public static final String JHS_KEY_A = "jhs:a";
    public static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("聚划算案例，每次1页，每页5条数据")
    @GetMapping("/product/find")
    public List<Product> find(int page, int size) {
        long start = (page - 1) * size;
        long end = start + size - 1;
        List list = redisTemplate.opsForList().range(JHS_KEY, start, end);
        if (CollectionUtils.isEmpty(list)) {
            // todo Redis找不到，去数据库中查询
        }
        log.info("参加活动的商家: {}", list);
        return list;
    }

    @ApiOperation("聚划算案例，AB双缓存，防止热key突然失效")
    @GetMapping("/product/findab")
    public List<Product> findAB(int page, int size) {
        List<Product> list = null;
        long start = (page - 1) * size;
        long end = start + size - 1;
        list = redisTemplate.opsForList().range(JHS_KEY_A, start, end);
        if (CollectionUtils.isEmpty(list)) {
            //  Redis找不到，去数据库中查询
            log.info("A缓存已经失效或活动已经结束");
            list = redisTemplate.opsForList().range(JHS_KEY_B, start, end);
            if (CollectionUtils.isEmpty(list)) {
                // todo Redis找不到，去数据库中查询
            }
        }
        log.info("参加活动的商家: {}", list);
        return list;
    }
}
