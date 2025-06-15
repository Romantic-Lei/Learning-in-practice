package com.luojia.demo.a05;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Slf4j
public class A05Application {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        // 可以解析 -> @ComponentScan @Bean @Import @ImportResource
        context.registerBean(ConfigurationClassPostProcessor.class);
        // 可以解析，@MapperScanner 注解
        context.registerBean(MapperScannerConfigurer.class, bd -> {
            bd.getPropertyValues().addPropertyValue("basePackage", "com.luojia.demo.a05.mapper");
        });

        // 初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 销毁容器
        context.close();

    }

}
