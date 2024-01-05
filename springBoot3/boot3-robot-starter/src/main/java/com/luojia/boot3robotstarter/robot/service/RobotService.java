package com.luojia.boot3robotstarter.robot.service;

import com.luojia.boot3robotstarter.robot.properties.RobotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    @Autowired
    RobotProperties robotProperties;

    public String sayHello() {
        return "你好, 【"+ robotProperties.getName() +"】, 年龄：【"+robotProperties.getAge()+"】";
    }
}
