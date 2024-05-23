package com.luojiapay.payment.controller;

import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(("/api/ali-pay"))
@Api(tags = "网站支付宝支付")
@Slf4j
public class AlipayController {

    @Autowired
    AliPayService aliPayService;

    @ApiOperation("统一收单下单并支付页面接口")
    @PostMapping("/trade/page/pay/{productId}")
    public Result tradePagePay(@PathVariable Long productId) {
        log.info("统一收单下单并支付页面接口");

        // 支付宝开发平台接收到 request 请求对象后，会为开发者生成一个html形式的form表单
        // 包含自动提交的脚本
        String formStr = aliPayService.tradeCreate(productId);
        // 我们将form表单字符串返回给前端，之后前端将会调用自动提交脚本，进行提交
        // 此时，表单会自动提交到action属性所指向的支付宝开放平台中，从而为用户展示一个支付页面
        return Result.ok().data("formStr", formStr);
    }
}
