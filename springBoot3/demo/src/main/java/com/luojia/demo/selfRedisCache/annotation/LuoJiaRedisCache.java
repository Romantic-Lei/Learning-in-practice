package com.luojia.demo.selfRedisCache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuoJiaRedisCache {

    // 设置Redis键的前缀
    String keyPrefix();

    // SpringEL表达式，解析占位符对应的匹配value值
    String matchValue();
}
