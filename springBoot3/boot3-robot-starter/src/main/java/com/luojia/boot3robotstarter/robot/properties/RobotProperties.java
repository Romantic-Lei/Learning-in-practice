package com.luojia.boot3robotstarter.robot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "robot")
@Component
@Data
public class RobotProperties {

    private String name;
    private String age;
    private String email;
}
