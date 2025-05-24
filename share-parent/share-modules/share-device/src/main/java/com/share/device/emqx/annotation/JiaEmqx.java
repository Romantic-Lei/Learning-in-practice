package com.share.device.emqx.annotation;

import java.lang.annotation.*;

// 自定义注解
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JiaEmqx {

    String topic();
}
