package com.luojiapay.payment.service;

import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;

public interface PaymentInfoService {

    void createPaymentInfo(Transaction transaction);
}
