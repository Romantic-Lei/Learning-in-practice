package com.luojiapay.payment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.luojiapay.payment.config.AlipayClientConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.entity.RefundInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.enums.alipay.AlipayTradeState;
import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.PaymentInfoService;
import com.luojiapay.payment.service.RefundInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
    RefundInfoService refundInfoService;
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
            log.info("响应结果：{}", response.getBody());

            if (response.isSuccess()) {
                log.info("调用成功");
                return response.getBody();
            } else {
                log.error("调用失败,订单在支付宝侧未创建");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.info("诊断链接:{}", diagnosisUrl);
                return null;
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据订单号查询支付宝支付查单接口，核实订单状态
     * 如果订单未创建，直接更新商户端订单状态
     * 如果订单未支付，则调用关单接口关闭订单，并更新商户端订单状态
     * 如果订单已支付，则更新商户端订单状态
     * @param orderNo
     */
    @Override
    public void checkOrderStatus(String orderNo) {
        String result = queryOrder(orderNo, null);

        // 订单未创建
        if (null == result) {
            // 订单未创建，不需要调用支付宝关单接口
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        // 解析所查单的响应结果
        Gson gson = new Gson();
        HashMap<String, LinkedTreeMap> resultMap = gson.fromJson(result, HashMap.class);
        LinkedTreeMap alipayTradeQueryResponse = resultMap.get("alipay_trade_query_response");
        // 订单状态
        String tradeStatus = (String) alipayTradeQueryResponse.get("trade_status");
        if (AlipayTradeState.NOTPAY.getType().equals(tradeStatus)) {
            // 如果订单未支付，则调用关单接口关闭订单，并更新商户端订单状态
            closeOrder(orderNo);
            // 关闭商户的订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }

        if (AlipayTradeState.SUCCESS.getType().equals(tradeStatus)) {
            log.info("核实订单已支付，更新订单状态并且记录支付日志");
            // 关闭商户的订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);

            // 订单支付成功，创建支付日志
            paymentInfoService.createPaymentInfoFromAlipay(alipayTradeQueryResponse);
        }
    }

    @Override
    public void refund(String orderNo, String reason) {
        try {
            // 创建退款单
            RefundInfo refundInfo = refundInfoService.createRefundByOrderNo(orderNo, reason);

            // 构造请求参数以调用接口
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            // 设置商户订单号
            model.setOutTradeNo(orderNo);
            // 设置查询选项
            List<String> queryOptions = new ArrayList<String>();
            queryOptions.add("refund_detail_item_list");
            model.setQueryOptions(queryOptions);
            BigDecimal divide = new BigDecimal(refundInfo.getTotalFee()).divide(new BigDecimal(100));
            // 设置退款金额
            model.setRefundAmount(divide.toString());
            // 设置退款原因说明
            model.setRefundReason(reason);
            // 设置退款请求号
            model.setOutRequestNo(refundInfo.getRefundNo());

            request.setBizModel(model);
            AlipayTradeRefundResponse response = alipayClient.execute(request);

            if (response.isSuccess()) {
                log.info("调用支付宝退款接口成功");
                // 更新订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_SUCCESS);
                // 更新退款单
                refundInfoService.updateRefundForAliPay(
                        refundInfo.getRefundNo(),
                        response.getBody(),
                        AlipayTradeState.REFUND_SUCCESS.getType());
            } else {
                log.info("调用失败");
                // 更新订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_ABNORMAL);
                // 更新退款单
                refundInfoService.updateRefundForAliPay(
                        refundInfo.getRefundNo(),
                        response.getBody(),
                        AlipayTradeState.REFUND_ERROR.getType());
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.info("diagnosisUrl:{}", diagnosisUrl);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String queryRefund(String orderNo) {
        try {
            // 构造请求参数以调用接口
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();

            // 设置商户订单号
            model.setOutTradeNo(orderNo);
            // 设置查询选项
            List<String> queryOptions = new ArrayList<String>();
            queryOptions.add("refund_detail_item_list");
            model.setQueryOptions(queryOptions);

            RefundInfo refundInfo = refundInfoService.queryRefundInfoByOrderNo(orderNo);
            // 默认订单号
            model.setOutRequestNo(orderNo);
            // 设置退款请求号
            if (null != refundInfo)
                model.setOutRequestNo(refundInfo.getRefundNo());

            request.setBizModel(model);
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);

            if (response.isSuccess()) {
                log.info("调用成功");
                return response.getBody();
            } else {
                log.info("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.error("diagnosisUrl:{}", diagnosisUrl);
                return response.getBody();
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String queryBill(String billDate, String type) {
        try {
            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
            AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType(type);
            model.setBillDate(billDate);
            request.setBizModel(model);
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);

            if (response.isSuccess()) {
                log.info("调用成功，返回结果 ===> {}", response.getBody());
                Gson gson = new Gson();
                HashMap<String, LinkedTreeMap> hashMap = gson.fromJson(response.getBody(), HashMap.class);
                LinkedTreeMap billDownloadurlResponse = hashMap.get("alipay_data_dataservice_bill_downloadurl_query_response");
                String billDownloadUrl = (String)billDownloadurlResponse.get("bill_download_url");
                return billDownloadUrl;
            } else {
                log.info("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.error("diagnosisUrl:{}", diagnosisUrl);
                return response.getBody();
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
