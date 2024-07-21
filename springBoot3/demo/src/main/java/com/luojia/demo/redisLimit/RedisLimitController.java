package com.luojia.demo.redisLimit;

import com.luojia.demo.redisLimit.annotation.RedisLimitAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RedisLimitController {

    /**
     * Redis+Lua脚本+AOP+反射+自定义注解，打造自定义基础架构限流组件
     * @return
     */
    @GetMapping("/redis/limit/test")
    @RedisLimitAnnotation(key = "redisLimit", permitsPerSecond = 3, expire = 10, msg = "当前访问人数过多，请稍后再试")
    public String redisLimit() {
        return "正常业务返回，订单流水：~~~";
    }

    @GetMapping("/redis/not/limit/test")
    public String redisNotLimit() {
        return "正常业务返回，订单流水：~~~";
    }
}
