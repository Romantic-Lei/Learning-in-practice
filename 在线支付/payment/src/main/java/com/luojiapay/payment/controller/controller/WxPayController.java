package com.luojiapay.payment.controller.controller;

import com.luojiapay.payment.service.WxPayService;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Api("网站微信支付API")
@RestController
@RequestMapping("/api/wx-pay")
@Slf4j
public class WxPayController {

    @Autowired
    WxPayService wxPayService;

    @ApiOperation("调用统一下单API，生成静态支付二维码")
    @PostMapping("/native/{productId}")
    public Result naticePay(@PathVariable Long productId) {
        log.info("发送支付请求");

        // 返回支付二维码链接和订单号
        Map<String, Object> map = wxPayService.nativePay(productId);
        return Result.ok().setData(map);
    }


}
