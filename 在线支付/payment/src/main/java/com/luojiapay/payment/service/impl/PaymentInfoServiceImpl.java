package com.luojiapay.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.luojiapay.payment.entity.PaymentInfo;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.mapper.PaymentInfoMapper;
import com.luojiapay.payment.service.PaymentInfoService;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    /**
     * 记录支付日志：微信支付
     * @param transaction
     */
    @Override
    public void createPaymentInfo(Transaction transaction) {
        log.info("记录支付日志：微信支付");
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
    /**
     * 记录支付日志：支付宝支付
     * @param paramsMap
     */
    @Override
    public void createPaymentInfoForAliPay(Map<String, String> paramsMap) {
        log.info("记录支付日志：支付宝支付");
        PaymentInfo paymentInfo = new PaymentInfo();
        // 获取订单号
        String outTradeNo = paramsMap.get("out_trade_no");
        // 业务编号
        String transactionId = paramsMap.get("trade_no");
        // 交易状态
        String tradeStatus = paramsMap.get("trade_status");
        // 交易金额
        String totalAmount = paramsMap.get("total_amount");
        String buyerPayAmount = paramsMap.get("buyer_pay_amount");
        int totalAmountValue = new BigDecimal(totalAmount).multiply(new BigDecimal(100)).intValue();
        int buyerPayAmountValue = new BigDecimal(buyerPayAmount).multiply(new BigDecimal(100)).intValue();

        paymentInfo.setOrderNo(outTradeNo);
        paymentInfo.setPaymentType(PayType.ALIPAY.getType());
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setTradeType("电脑网站支付");
        paymentInfo.setTradeState(tradeStatus);
        paymentInfo.setTotal(totalAmountValue);
        paymentInfo.setPayerTotal(buyerPayAmountValue);
        paymentInfo.setTradeStateDesc(tradeStatus);
        paymentInfo.setContent(JSON.toJSONString(paramsMap));
        baseMapper.insert(paymentInfo);
    }

    @Override
    public void createPaymentInfoFromAlipay(LinkedTreeMap alipayTradeQueryResponse) {
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setOrderNo((String) alipayTradeQueryResponse.get("out_trade_no"));
        paymentInfo.setTransactionId((String) alipayTradeQueryResponse.get("trade_no"));
        paymentInfo.setPaymentType(PayType.ALIPAY.getType());
        paymentInfo.setTradeType("电脑网站支付");
        paymentInfo.setTradeState((String) alipayTradeQueryResponse.get("trade_status"));
        paymentInfo.setTradeStateDesc((String) alipayTradeQueryResponse.get("trade_status"));
        int totalAmount = new BigDecimal((String) alipayTradeQueryResponse.get("total_amount")).multiply(new BigDecimal(100)).intValue();
        int buyerPayAmount = new BigDecimal((String) alipayTradeQueryResponse.get("buyer_pay_amount")).multiply(new BigDecimal(100)).intValue();
        paymentInfo.setTotal(totalAmount);
        paymentInfo.setPayerTotal(buyerPayAmount);
        paymentInfo.setContent(JSON.toJSONString(alipayTradeQueryResponse));
        baseMapper.insert(paymentInfo);
    }
}
