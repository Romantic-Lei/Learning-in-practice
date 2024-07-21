package com.luojia.demo.redisLimit.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLimitAnnotation {

    /**
     * 资源的key，唯一
     * 作用：不同的接口，不同的流量控制
     */
    String key() default "";

    /**
     * 指定时间内允许访问的次数
     */
    long permitsPerSecond() default 3;

    /**
     * key 的过期时间，单位秒，默认30
     */
    long expire() default 30;

    /**
     * 默认提示语
     */
    String msg() default "系统繁忙or访问频次太高，请稍后再试~";
}
