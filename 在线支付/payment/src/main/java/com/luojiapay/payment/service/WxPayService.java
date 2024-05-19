package com.luojiapay.payment.service;

import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;

import java.util.Map;

public interface WxPayService {
    Map<String, Object> nativePay(Long productId);

    void processOrder(Transaction transaction);
}
