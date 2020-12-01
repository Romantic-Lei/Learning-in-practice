package com.romanticlei.study.interview.spring.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewApplication {

    public static void main(String[] args) {
        System.out.println("服务启动开始");
        SpringApplication.run(InterviewApplication.class, args);
        System.out.println("服务启动完成");
    }

}
