package com.luojiapay.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.luojiapay.payment.entity.PaymentInfo;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.mapper.PaymentInfoMapper;
import com.luojiapay.payment.service.PaymentInfoService;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Override
    public void createPaymentInfo(Transaction transaction) {
        log.info("记录支付日志");
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(transaction.getOutTradeNo());
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setTransactionId(transaction.getTransactionId());
        // 交易类型
        paymentInfo.setTradeType(transaction.getTradeType().name());
        paymentInfo.setTradeState(transaction.getTradeState().name());
        paymentInfo.setTradeStateDesc(transaction.getTradeStateDesc());
        paymentInfo.setTotal(transaction.getAmount().getTotal());
        paymentInfo.setPayerTotal(transaction.getAmount().getPayerTotal());
        paymentInfo.setContent(transaction.toString());
        baseMapper.insert(paymentInfo);
    }
}
