package com.luojiapay.payment.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luojiapay.payment.service.WxPayService;
import com.luojiapay.payment.util.HttpUtils;
import com.luojiapay.payment.vo.Result;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@Api("网站微信支付API")
@RestController
@RequestMapping("/api/wx-pay")
@Slf4j
public class WxPayController {

    @Autowired
    WxPayService wxPayService;
    @Autowired
    Config config;

    @ApiOperation("调用统一下单API，生成静态支付二维码")
    @PostMapping("/native/{productId}")
    public Result naticePay(@PathVariable Long productId) {
        log.info("发送支付请求");

        // 返回支付二维码链接和订单号
        Map<String, Object> map = wxPayService.nativePay(productId);
        return Result.ok().setData(map);
    }

    @ApiOperation("微信回调通知报文")
    @PostMapping("/native/notify")
    public String nativeNotify(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();// 应答对象

        // 处理通知参数
        String body = HttpUtils.readData(request);
        Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
        log.info("支付通知的完整数据 ===> {}", body);
        log.info("支付通知的id ===> {}", bodyMap.get("id"));

        // 验签
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(request.getHeader("Wechatpay-Serial"))
                .nonce(request.getHeader("Wechatpay-Nonce"))
                .signature(request.getHeader("Wechatpay-Signature"))
                .timestamp(request.getHeader("Wechatpay-Timestamp"))
                .body(body)
                .build();
        NotificationParser parser = new NotificationParser((NotificationConfig)config);

        Transaction transaction = new Transaction();
        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            transaction = parser.parse(requestParam, Transaction.class);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("sign verification failed", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            map.put("code", "fail");
            map.put("message", "失败");
            return gson.toJson(map);
        }
        log.error("sign verification success");

        // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
        // if (/* process error */) {
        //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        // }

        // todo: 验签解密后处理订单
        wxPayService.processOrder(transaction);


        // 成功应答
        response.setStatus(200);
        map.put("code", "SUCCESS");
        map.put("message", "成功");
        return gson.toJson(map);
    }

}
