package com.luojiapay.payment.service;

import com.google.gson.internal.LinkedTreeMap;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;

import java.util.Map;

public interface PaymentInfoService {

    void createPaymentInfo(Transaction transaction);

    void createPaymentInfoForAliPay(Map<String, String> paramsMap);

    void createPaymentInfoFromAlipay(LinkedTreeMap alipayTradeQueryResponse);
}
