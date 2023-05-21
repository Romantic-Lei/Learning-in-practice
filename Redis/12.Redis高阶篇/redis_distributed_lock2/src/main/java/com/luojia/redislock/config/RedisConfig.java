package com.luojia.redislock.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * *redis序列化的工具定置类，下面这个请一定开启配置
     * *127.0.0.1:6379> keys *
     * *1) “ord:102” 序列化过
     * *2)“\xaclxedlxeelx05tixeelaord:102” 野生，没有序列化过
     * *this.redisTemplate.opsForValue(); //提供了操作string类型的所有方法
     * *this.redisTemplate.opsForList();// 提供了操作List类型的所有方法
     * *this.redisTemplate.opsForset(); //提供了操作set类型的所有方法
     * *this.redisTemplate.opsForHash(); //提供了操作hash类型的所有方认
     * *this.redisTemplate.opsForZSet(); //提供了操作zset类型的所有方法
     * param LettuceConnectionFactory
     * return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // 设置key序列化方式string
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value的序列化方式json，使用GenericJackson2JsonRedisSerializer替换默认序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(0)
                .setPassword("123456");
        return (Redisson) Redisson.create(config);
    }

}
