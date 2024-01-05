package com.luojia.boot3robotstarter.robot.autoConfiguration;

import com.luojia.boot3robotstarter.robot.controller.RobotController;
import com.luojia.boot3robotstarter.robot.properties.RobotProperties;
import com.luojia.boot3robotstarter.robot.service.RobotService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RobotController.class, RobotService.class, RobotProperties.class})
public class RobotAutoConfiguration {
}
