package com.luojia.demo.a06;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyBean implements BeanNameAware, ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MyBean.class);
    @Override
    public void setBeanName(String name) {
        log.info("【第一被执行】当前bean： {}, 名字叫： {}", this, name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("【第二被执行】当前bean： {}, 容器是： {}", this, applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【第四被执行】当前bean： {}, 属性设置完毕", this);
    }

    @Autowired
    public void aaa(ApplicationContext applicationContext) {
        log.info("【第零被执行】当前bean： {}, 使用@Autowired 容器是： {}", this, applicationContext);
    }

    @PostConstruct
    public void init() {
        log.info("【第三被执行】当前bean： {} 被初始化", this);
    }

}
