package com.luojiapay.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.luojiapay.payment.config.AlipayClientConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    Environment env;

    private final ReentrantLock lock = new ReentrantLock();

    @Transactional
    @Override
    public String tradeCreate(Long productId) {
        try {
            // 生成订单
            OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId, 2L, PayType.ALIPAY.getType());

            // 调用支付宝支付接口
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();

            // 设置商户门店编号
            model.setStoreId("NJ_001");
            // 设置订单绝对超时时间
            // model.setTimeExpire("2016-12-31 10:05:01");
            // 设置订单标题
            model.setSubject(orderInfo.getTitle());
            // 设置请求来源地址
            model.setRequestFromUrl("https://");
            // 设置产品码 目前电脑支付场景下仅支持 FAST_INSTANT_TRADE_PAY
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            // 设置PC扫码支付的方式
            model.setQrPayMode("1");
            // 设置商户自定义二维码宽度
            model.setQrcodeWidth(100L);
            // 设置请求后页面的集成方式
            model.setIntegrationType("PCWEB");
            BigDecimal divide = new BigDecimal(orderInfo.getTotalFee().toString()).divide(new BigDecimal(100));

            // 设置订单包含的商品列表信息
            List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
            GoodsDetail goodsDetail0 = new GoodsDetail();
            goodsDetail0.setGoodsName(orderInfo.getTitle());
            goodsDetail0.setQuantity(1L);// 商品数量
            goodsDetail0.setPrice(divide.toString());
            goodsDetail0.setGoodsId(productId+"");
            goodsDetail.add(goodsDetail0);
            model.setGoodsDetail(goodsDetail);

            // 设置商户的原始订单号
            model.setMerchantOrderNo("20161008001");
            // 设置商户订单号
            model.setOutTradeNo(orderInfo.getOrderNo());

            // 设置订单总金额, 单位是元
            model.setTotalAmount(divide.toString());

            request.setBizModel(model);
            // 接口回调地址
            request.setNotifyUrl(env.getProperty("alipay.notify-url"));
            // 支付成功跳转页面
            request.setReturnUrl(env.getProperty("alipay.return-url"));
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            // 如果需要返回GET请求，请使用
            // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            String pageRedirectionData = response.getBody();
            System.out.println(pageRedirectionData);

            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
                return response.getBody();
            } else {
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.error("调用失败，诊断链接：{}", diagnosisUrl);
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理订单
     * @param paramsMap
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Map<String, String> paramsMap) {
        log.info("处理订单");
        String outTradeNo = paramsMap.get("out_trade_no");
        if (lock.tryLock()) {
            try {
                // 处理重复通知，保证接口的幂等性
                String orderStatus = orderInfoService.getOrderStatus(outTradeNo);
                if (!OrderStatus.NOTPAY.getType().equals(orderStatus)) {
                    return;
                }

                // 更新订单状态
                orderInfoService.updateStatusByOrderNo(outTradeNo, OrderStatus.SUCCESS);
                // 记录支付日志
                paymentInfoService.createPaymentInfoForAliPay(paramsMap);
            } finally {
                // 主动释放锁
                lock.unlock();
            }
        }
    }

    /**
     * 用户取消订单
     * @param orderNo
     */
    @Override
    public void cancelOrder(String orderNo) {
        // 调用支付宝提供的统一收单交易关闭接口
        closeOrder(orderNo);
        // 更新用户订单状态
        orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CANCEL);
    }

    @Override
    public String queryOrder(String orderNo, String tradeNo) {
        try {
            log.info("支付宝查单接口调用 ===> {}", orderNo);

            // 构造请求参数以调用接口
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();

            // 设置订单支付时传入的商户订单号
            model.setOutTradeNo(orderNo);
            // 设置支付宝交易号
            model.setTradeNo(tradeNo);

            // 设置查询选项
            // List<String> queryOptions = new ArrayList<String>();
            // queryOptions.add("trade_settle_info");
            // model.setQueryOptions(queryOptions);

            request.setBizModel(model);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            System.out.println(response.getBody());

            if (response.isSuccess()) {
                log.info("调用成功");
                return response.getBody();
            } else {
                log.error("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.info("诊断链接:{}", diagnosisUrl);
                throw new RuntimeException("支付宝查询交易接口调用失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关单接口
     * @param orderNo
     */
    private void closeOrder(String orderNo) {
        try {
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderNo);
            request.setBizContent(bizContent.toString());
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
            } else {
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.error("调用失败，诊断链接：{}", diagnosisUrl);
                throw new RuntimeException("关闭支付交易失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
