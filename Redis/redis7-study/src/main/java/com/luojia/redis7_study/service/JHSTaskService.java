package com.luojia.redis7_study.service;

import com.luojia.redis7_study.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class JHSTaskService {

    public static final String JHS_KEY = "jhs";
    public static final String JHS_KEY_A = "jhs:a";
    public static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 假设此处是从数据库读取，然后加载到redis
     * @return
     */
    private List<Product> getProductsFromMysql() {
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int id = random.nextInt(10000);
            Product product = new Product((long) id, "product" + i, i, "detail");
            list.add(product);
        }
        return list;
    }

}
