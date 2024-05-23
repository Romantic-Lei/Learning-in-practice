package com.luojiapay.payment.service;

import com.alipay.api.AlipayApiException;

public interface AliPayService {
    String tradeCreate(Long productId);
}
