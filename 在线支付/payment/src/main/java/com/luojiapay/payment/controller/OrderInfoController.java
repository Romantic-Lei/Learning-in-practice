package com.luojiapay.payment.controller;

import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Api("商品订单管理API")
@RestController
@RequestMapping("/api/order-info")
@Slf4j
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    @GetMapping("/list")
    public Result list() {
        List<OrderInfo> list = orderInfoService.queryOrderByCreateTimeDesc();
        return Result.ok().data("list", list);
    }

}
