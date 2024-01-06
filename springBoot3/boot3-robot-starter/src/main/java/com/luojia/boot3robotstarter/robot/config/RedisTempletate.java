package com.luojia.boot3robotstarter.robot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisTempletate {

    /**
     * redis 底层帮我们实现了两个模版，一个是 RedisTemplate<Object, Object> 一个是 StringRedisTemplate
     * StringRedisTemplate 里面的key 和Value 都是String类型的
     * RedisTemplate<Object, Object> 里面的key 和Value 都可以存放对象
     * 当没有设置序列化器的时候，RedisTemplate会使用默认的jdk序列化器 JdkSerializationRedisSerializer，
     * 我们将对象存入到Redis后，看到的结果会是一串乱码，数据不可视（双击shift，输入RedisAutoConfiguration 可以查看）
     * 我们将他实现的RedisTemplate bean拷贝过来，自己设置序列化器
     * 进入 setDefaultSerializer 后，发现参数是 RedisSerializer对象，点进去，按ctrl + H 查看该接口的实现类（idea）
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
