package com.luojia.canaldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CanaldemoApplication {

    // 只是一个canal监听，不需要主启动类
    public static void main(String[] args) {
        SpringApplication.run(CanaldemoApplication.class, args);
    }

}
