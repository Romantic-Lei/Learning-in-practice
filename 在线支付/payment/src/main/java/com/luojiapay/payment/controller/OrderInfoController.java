package com.luojiapay.payment.controller;

import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(tags = "商品订单管理API")
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

    @ApiOperation("根据订单编号查询订单状态")
    @GetMapping("/query-order-status/{orderNo}")
    public Result queryOrderByStatus(@PathVariable String orderNo) {
        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if (OrderStatus.SUCCESS.getType().equals(orderStatus)) {
            return Result.ok().setMessage("支付成功");
        }
        return Result.ok().setCode(101).setMessage("支付中...");
    }

}
