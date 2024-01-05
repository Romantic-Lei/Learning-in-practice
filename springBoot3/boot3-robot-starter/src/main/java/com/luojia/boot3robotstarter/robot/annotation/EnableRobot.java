package com.luojia.boot3robotstarter.robot.annotation;

import com.luojia.boot3robotstarter.robot.autoConfiguration.RobotAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({RobotAutoConfiguration.class})
public @interface EnableRobot {
}
