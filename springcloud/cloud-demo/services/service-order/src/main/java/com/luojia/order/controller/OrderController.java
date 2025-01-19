package com.luojia.order.controller;

import com.luojia.order.config.OrderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("config")
    public String config(){
        return "order.timeout = " + orderProperties.getTimeout() + ": " +
                "order.auto-config = " + orderProperties.getAutoConfirm() + ": " +
                "order.dbUrl = " + orderProperties.getDbUrl();
    }

}
