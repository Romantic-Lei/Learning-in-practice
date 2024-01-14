package com.luojia.boot3robotstarter.robot.controller;

import com.luojia.boot3robotstarter.robot.service.RobotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {

        Thread thread = new Thread("nn");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 8, 10, TimeUnit.SECONDS
                , new LinkedBlockingQueue(100),
                (r -> {
                    return new Thread(r,"nn");
                }),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在执行");
                }
            });
        }
    }

}
