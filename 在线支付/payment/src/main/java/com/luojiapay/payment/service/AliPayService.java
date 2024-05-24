package com.luojiapay.payment.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AliPayService {
    String tradeCreate(Long productId);

    void processOrder(Map<String, String> paramsMap);
}
