package com.luojiapay.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.impl.AliPayServiceImpl;
import com.luojiapay.payment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(("/api/ali-pay"))
@Api(tags = "网站支付宝支付")
@Slf4j
public class AlipayController {

    @Autowired
    AliPayService aliPayService;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    Environment env;

    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    @GetMapping("/threadLock")
    public void threadLock() {


        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " got resource1 lock >>>");
                try {
                    Thread.sleep(1000); // 模拟工作时间，让死锁更容易观察到
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " trying to get resource2 lock.");
                synchronized (resource2) { // 尝试获取resource2的锁
                    System.out.println(Thread.currentThread().getName() + " got resource2 lock...");
                }
            }
        }, "Thread 1");

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " got resource2 lock >>>");
                try {
                    Thread.sleep(1000); // 模拟工作时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " trying to get resource1 lock.");
                synchronized (resource1) { // 尝试获取resource1的锁
                    System.out.println(Thread.currentThread().getName() + " got resource1 lock...");
                }
            }
        }, "Thread 2");

        thread1.start();
        thread2.start();
    }


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

    @ApiOperation("统一收单下单并支付页面接口")
    @PostMapping("/trade/notify")
    public String tradePagePay(@RequestParam Map<String, String> paramsMap) {
        log.info("支付通知正在执行，通知参数：{}", paramsMap);
        String result = "failure";

        try {
            // 异步通知验签
            boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap,
                    env.getProperty("alipay.alipay-public-key"),
                    AlipayConstants.CHARSET_UTF8,
                    AlipayConstants.SIGN_TYPE_RSA2); //调用SDK验证签名
            if(!signVerified){
                // 验签失败则记录异常日志，并在response中返回failure.
                log.error("支付成功，异步通知验签失败");
                return result;
            }

            // 验签成功后
            log.info("支付成功，异步通知验签成功");
            // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，
            String outTradeNo = paramsMap.get("out_trade_no");
            // 1.out_trade_no 是否为商家系统中创建的订单号
            OrderInfo orderByOrderNo = orderInfoService.getOrderByOrderNo(outTradeNo);
            if (orderByOrderNo == null) {
                log.info("订单不存在");
                return result;
            }

            // 2.判断 total_amount 是否确实为该订单的实际金额
            String totalAmount = paramsMap.get("total_amount");
            int totalAmountValue = new BigDecimal(totalAmount).multiply(new BigDecimal(100)).intValue();
            int totalFeeInt = orderByOrderNo.getTotalFee().intValue();
            if (totalFeeInt != totalAmountValue) {
                log.error("金额校验失败");
                return result;
            }

            // 3.校验通知中的 seller_id（或者 seller_email）是否为 out_trade_no 这笔单据的对应的操作方
            String sellerId = paramsMap.get("seller_id");
            if (!env.getProperty("alipay.seller-id").equals(sellerId)) {
                log.error("商家PID校验失败");
                return result;
            }

            // 4.验证 app_id 是否为该商家本身
            String appId = paramsMap.get("app_id");
            if (!env.getProperty("alipay.app-id").equals(appId)) {
                log.error("appId校验失败");
                return result;
            }

            // 只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功
            String tradeStatus = paramsMap.get("trade_status");
            if (!"TRADE_SUCCESS".equals(tradeStatus)) {
                log.info("支付未成功");
                return result;
            }

            // 处理业务，修改订单状态，记录支付日志
            aliPayService.processOrder(paramsMap);

            // 校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure


            // 向支付宝发送成功的通知
            result = "success";
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @ApiOperation("用户取消订单")
    @PostMapping("/trade/close/{orderNo}")
    public Result canel(@PathVariable String orderNo) {
        log.info("支付宝支付取消订单");
        aliPayService.cancelOrder(orderNo);
        return Result.ok().setMessage("订单已取消");
    }

    @ApiOperation("查询订单")
    @GetMapping("/trade/query/{orderNo}")
    public Result queryOrder(@PathVariable String orderNo, @RequestParam(required = false) String tradeNo) {
        log.info("查询订单，订单号：{}, 支付宝交易号：{}", orderNo, tradeNo);

        String result = aliPayService.queryOrder(orderNo, tradeNo);
        return Result.ok().setMessage("查询成功").data("result", result);
    }

    @ApiOperation("申请退款")
    @PostMapping("/trade/refund/{orderNo}/{reason}")
    public Result refunds(@PathVariable String orderNo,
                          @PathVariable String reason) {
        log.info("支付宝申请退款，订单号：{}, 退款原因：{}", orderNo, reason);
        aliPayService.refund(orderNo, reason);

        return Result.ok();
    }

    @ApiOperation("查询退款接口")
    @GetMapping("/trade/fastpay/refund/query/{orderNo}")
    public Result refunds(@PathVariable String orderNo) {
        log.info("支付宝申请退款，订单号：{}", orderNo);
        String result = aliPayService.queryRefund(orderNo);

        return Result.ok().setMessage("查询成功").data("result", result);
    }

    @ApiOperation("获取账单下载地址接口")
    @GetMapping("/bill/downloadurl/query/{billDate}/{type}")
    public Result queryBill(@PathVariable String billDate,
                          @PathVariable String type) {
        log.info("支付宝获取账单url");
        String downloadUrl = aliPayService.queryBill(billDate, type);

        return Result.ok().setMessage("获取账单url成功").data("downloadUrl", downloadUrl);
    }
}
