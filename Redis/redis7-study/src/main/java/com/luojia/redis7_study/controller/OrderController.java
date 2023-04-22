package com.luojia.redis7_study.controller;

import com.luojia.redis7_study.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags="订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("新增订单")
    @PostMapping("/order/add")
    public void addOrder() {
        orderService.addOrder();
    }

    @ApiOperation("根据keyId查询订单")
    @GetMapping("/order/query")
    public String queryOrder(Integer keyId) {
        return orderService.getOrderById(keyId);
    }

}
