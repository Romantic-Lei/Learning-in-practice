package com.luojiapay.payment.controller;

import com.google.gson.Gson;
import com.luojiapay.payment.service.WxPayService;
import com.luojiapay.payment.util.HttpUtils;
import com.luojiapay.payment.vo.Result;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api(tags = "网站微信支付API")
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

    @ApiOperation("微信支付-微信回调通知报文")
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

        // 验签解密后处理订单
        wxPayService.processOrder(transaction);

        // 成功应答
        response.setStatus(200);
        map.put("code", "SUCCESS");
        map.put("message", "成功");
        return gson.toJson(map);
    }

    @ApiOperation("取消订单")
    @PostMapping("/cancel/{orderNo}")
    public Result cancel(@PathVariable String orderNo) {
        log.info("取消订单，订单号：{}", orderNo);
        wxPayService.cancelOrder(orderNo);

        return Result.ok();
    }

    @ApiOperation("查询订单")
    @GetMapping("/query/{orderNo}")
    public Result queryOrder(@PathVariable String orderNo) {
        log.info("查询订单，订单号：{}", orderNo);
        com.wechat.pay.java.service.payments.model.Transaction result = wxPayService.queryOrder(orderNo);

        return Result.ok().setMessage("查询成功").data("result", result);
    }

    @ApiOperation("申请退款")
    @PostMapping("/refunds/{orderNo}/{reason}")
    public Result refunds(@PathVariable String orderNo,
                          @PathVariable String reason) {
        log.info("申请退款，订单号：{}, 退款原因：{}", orderNo, reason);
        wxPayService.refund(orderNo, reason);

        return Result.ok();
    }

    @ApiOperation("查询退款信息")
    @GetMapping("/query-refund/{refundNo}")
    public Result queryRefund(@PathVariable String refundNo) {
        log.info("查询退款订单，退款单号：{}", refundNo);
        Refund refund = wxPayService.queryReFund(refundNo);

        return Result.ok().setMessage("查询成功").data("result", refund);
    }

    @ApiOperation("退款通知-微信回调商户客户端")
    @PostMapping("/refunds/notify")
    public String refundsNotify(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();// 应答对象

        // 处理通知参数
        String body = HttpUtils.readData(request);
        Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
        log.info("退款通知的完整数据 ===> {}", body);
        log.info("退款通知的id ===> {}", bodyMap.get("id"));

        // 验签
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(request.getHeader("Wechatpay-Serial"))
                .nonce(request.getHeader("Wechatpay-Nonce"))
                .signature(request.getHeader("Wechatpay-Signature"))
                .timestamp(request.getHeader("Wechatpay-Timestamp"))
                .body(body)
                .build();
        NotificationParser parser = new NotificationParser((NotificationConfig) config);

        RefundNotification transaction = new RefundNotification();
        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            transaction = parser.parse(requestParam, RefundNotification.class);
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("sign verification failed", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            map.put("code", "fail");
            map.put("message", "失败");
            return gson.toJson(map);
        }

        log.error("sign verification success");

        // 验签解密后处理订单
        wxPayService.processRefund(transaction);
        // 成功应答
        response.setStatus(200);
        map.put("code", "SUCCESS");
        map.put("message", "成功");
        return gson.toJson(map);
    }

    @ApiOperation("交易账单/资金账单查询")
    @GetMapping("/querybill/{billDate}/{type}")
    public Result querybill(@PathVariable String billDate,
                                @PathVariable String type) {
        log.info("获取账单url");

        String downloadUrl = wxPayService.queryBill(billDate, type);
        return Result.ok().setMessage("获取账单url成功").data("downloadUrl", downloadUrl);
    }

    @ApiOperation("交易账单/资金账单下载")
    @GetMapping("/downloadbill/{billDate}/{type}")
    public Result downloadbill(@PathVariable String billDate,
                                @PathVariable String type) throws IOException {
        log.info("下载账单url");

        String result = wxPayService.downloadbill(billDate, type);
        return Result.ok().setMessage("获取账单url成功").data("result", result);
    }
}
