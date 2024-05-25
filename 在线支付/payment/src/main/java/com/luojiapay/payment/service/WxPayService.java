package com.luojiapay.payment.service;

import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;

import java.io.IOException;
import java.util.Map;

public interface WxPayService {
    Map<String, Object> nativePay(Long productId);

    void processOrder(Transaction transaction);

    void cancelOrder(String orderNo);

    com.wechat.pay.java.service.payments.model.Transaction queryOrder(String orderNo);

    void refund(String orderNo, String reason);

    void checkOrderStatus(String orderNo);

    Refund queryReFund(String refundNo);

    void processRefund(RefundNotification transaction);

    String queryBill(String billDate, String type);

    String downloadbill(String billDate, String type) throws IOException;

    void checkRefundStatus(String refundNo);
}
