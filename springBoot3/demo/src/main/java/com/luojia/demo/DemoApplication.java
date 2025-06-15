package com.luojia.demo;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.luojia.demo.mapper")
// @Import(RobotAutoConfiguration.class)
// @EnableRobot
// @ComponentScan("com.luojia.boot3robotstarter.robot")
public class DemoApplication {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        Map<String, Object> map = (Map<String, Object>)singletonObjects.get(beanFactory);

        map.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " : " + e.getValue());
        });

        System.out.println("----");
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        System.out.println("----");
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

}
