package com.luojia.order.config;

import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrderConfig {

    /**
     * Feign 的日志打印规格
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Feign 默认是不开启重试的，这里我们手动的配置开启重试
     * @return
     */
    @Bean
    Retryer retryer() {
        return new Retryer.Default();

    }

}
