package com.luojia.demo;

import com.luojia.boot3robotstarter.robot.annotation.EnableRobot;
import com.luojia.boot3robotstarter.robot.autoConfiguration.RobotAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.luojia.demo.mapper")
// @Import(RobotAutoConfiguration.class)
// @EnableRobot
// @ComponentScan("com.luojia.boot3robotstarter.robot")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
