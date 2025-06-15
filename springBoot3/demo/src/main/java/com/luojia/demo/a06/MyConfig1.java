package com.luojia.demo.a06;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig1 {

    private static final Logger log = LoggerFactory.getLogger(MyConfig1.class);

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.info("注入：Myconfig1.setApplicationContext");
    }

    @PostConstruct
    public void init() {
        log.info("初始化：Myconfig1.init");
    }

    @Bean
    public BeanFactoryPostProcessor processor1() {
        // 加上这个bean之后，上面的@Autowired 和 @PostConstruct 都失效了
        return beanFactory -> {
            log.info("@Bean 执行：Myconfig1.processor1");
        };
    }

}
