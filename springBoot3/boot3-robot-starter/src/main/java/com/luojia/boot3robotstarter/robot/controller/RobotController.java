package com.luojia.boot3robotstarter.robot.controller;

import com.luojia.boot3robotstarter.robot.service.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "机器控制类")
@RestController
public class RobotController {

    @Autowired
    RobotService robotService;

    @Operation(summary = "查询", description = "说你好")
    @GetMapping("/robot/hello")
    public String sayHello() {
        return robotService.sayHello();
    }

}
