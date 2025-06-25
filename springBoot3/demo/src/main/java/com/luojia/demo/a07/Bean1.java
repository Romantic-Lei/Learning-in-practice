package com.luojia.demo.a07;

import com.luojia.demo.a06.MyConfig1;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class Bean1 implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(Bean1.class);
    public Bean1() {
        log.info("Bean1 创建");
    }

    @PostConstruct
    public void init() {
        log.info("Bean1 初始化1");
    }

    // 如果实现了 Aware 接口，则会介于注解和实现 InitializingBean 之间
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Bean1 初始化完成2");
    }

    public void init3() {
        log.info("Bean1 初始化3");
    }
}
