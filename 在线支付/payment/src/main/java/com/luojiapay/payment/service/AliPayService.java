package com.luojiapay.payment.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AliPayService {
    String tradeCreate(Long productId);

    void processOrder(Map<String, String> paramsMap);

    void cancelOrder(String orderNo);

    String queryOrder(String orderNo, String tradeNo);

    void checkOrderStatus(String orderNo);

    void refund(String orderNo, String reason);

    String queryRefund(String orderNo);

    String queryBill(String billDate, String type);
}
