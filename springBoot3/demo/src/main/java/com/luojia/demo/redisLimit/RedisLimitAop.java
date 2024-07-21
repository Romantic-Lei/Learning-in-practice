package com.luojia.demo.redisLimit;

import com.luojia.demo.redisLimit.annotation.RedisLimitAnnotation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Slf4j
@Aspect
@Component
public class RedisLimitAop {
    Object result = null;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private DefaultRedisScript<Long> redisLuaScript;

    @PostConstruct
    public void init() {
        redisLuaScript = new DefaultRedisScript<>();
        redisLuaScript.setResultType(Long.class);
        redisLuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimit.lua")));
    }

    @Around("@annotation(com.luojia.demo.redisLimit.annotation.RedisLimitAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("-------环绕通知前置通知");
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取到切面方法
        Method method = signature.getMethod();
        // 判断该方法是否有 RedisLimitAnnotation 注解，存在即需要限流操作
        RedisLimitAnnotation redisLimitAnnotation = method.getAnnotation(RedisLimitAnnotation.class);

        if (redisLimitAnnotation != null) {
            String key = redisLimitAnnotation.key();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();

            String limitKey = key + "\t" + className + "\t" + methodName;
            log.info(limitKey);

            if(null == key) {
                throw new RuntimeException("limit key cannot be null");
            }

            long limit = redisLimitAnnotation.permitsPerSecond();
            long expire = redisLimitAnnotation.expire();
            List<String> keys = Collections.singletonList(key);

            Long count = stringRedisTemplate.execute(
                    redisLuaScript,
                    keys,
                    String.valueOf(limit),
                    String.valueOf(expire));

            System.out.println("Access try count is " + count + "\t key = " + key);
            if (count != null && count == 0) {
                System.out.println("启动限流功能 key：" + key);
                return redisLimitAnnotation.msg();
            }

            try {
                result = joinPoint.proceed();//放行
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            System.out.println("-------环绕通知后置通知");
            System.out.println();
            System.out.println();
        }

        return result;
    }

}
