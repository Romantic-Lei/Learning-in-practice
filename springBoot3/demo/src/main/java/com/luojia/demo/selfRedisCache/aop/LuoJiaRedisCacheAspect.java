package com.luojia.demo.selfRedisCache.aop;

import com.luojia.demo.selfRedisCache.annotation.LuoJiaRedisCache;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LuoJiaRedisCacheAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.luojia.demo.selfRedisCache.annotation.LuoJiaRedisCache)")
    public void chchePointCut() {}

    @Around("chchePointCut()")
    public Object doCache(ProceedingJoinPoint joinPoint) {
        Object result = null;

        try {
            // 1.获得重载后的方法名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // 2.确定方法名后获得该方法上
            LuoJiaRedisCache luoJiaRedisCache = method.getAnnotation(LuoJiaRedisCache.class);
            // 3.拿到注解标签，获得该注解上面配置的参数进行封装和调用
            String keyPrefix = luoJiaRedisCache.keyPrefix();
            String matchValue = luoJiaRedisCache.matchValue();
            // 4.SpringEL 解析器
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(matchValue); // #id
            StandardEvaluationContext context = new StandardEvaluationContext();

            // 5.获得方法里面的形参个数
            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                System.out.println("获得方法里参数名和值：" + parameterNames[i] + "\t" + args[i].toString());
                context.setVariable(parameterNames[i], args[i].toString());
            }

            // 6.通过上述，拼接redis 的最终key形式
            String key = keyPrefix + ":" + expression.getValue(context).toString();
            System.out.println("-----拼接redis的最终key形式：" + key);

            // 7.先去redis 里面查询看看有没有
            result = redisTemplate.opsForValue().get(key);
            if (result != null) {
                System.out.println("------redis 里面有数据，直接返回结果" + result);
                return result;
            }

            // 8.redis 没有，去MySQL查询数据
            result = joinPoint.proceed();
            // 9.mysql 步骤结束，将数据存放到redis
            if (result != null) {
                System.out.println("--------redis 无数据，将结果放入到redis中");
                redisTemplate.opsForValue().set(key, result);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
