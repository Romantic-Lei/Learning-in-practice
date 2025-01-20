package com.luojia.order.controller;

import com.luojia.order.config.OrderProperties;
import com.luojia.order.feign.WeatherFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderProperties orderProperties;

    @Autowired
    WeatherFeignClient weatherFeignClient;

    @GetMapping("config")
    public String config(){
        return "order.timeout = " + orderProperties.getTimeout() + ": " +
                "order.auto-config = " + orderProperties.getAutoConfirm() + ": " +
                "order.dbUrl = " + orderProperties.getDbUrl();
    }

    @GetMapping("weather")
    public String weather(){
        String weather = weatherFeignClient.getWeather("APPCODE 93b7e19861a24c519a7548b17dc16d75",
                "50b53ff8dd7d9fa320d3d3ca32cf8ed1",
                "2182");

        System.out.println("weather = " + weather);
        return weather;
    }

}
