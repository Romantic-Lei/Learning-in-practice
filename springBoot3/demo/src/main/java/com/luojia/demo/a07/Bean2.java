package com.luojia.demo.a07;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class Bean2 implements DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(Bean2.class);

    public Bean2() {
        log.info("Bean2 创建");
    }

    @PreDestroy
    public void destroy1() {
        log.info("Bean2 销毁1");
    }

    @Override
    public void destroy() throws Exception {
        log.info("Bean2 销毁2");
    }

    public void destroy3() {
        log.info("Bean2 销毁3");
    }
}
