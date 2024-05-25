package com.luojiapay.payment.enums.alipay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlipayTradeState {

    /**
     * 支付成功,交易支付成功
     */
    SUCCESS("TRADE_SUCCESS"),

    /**
     * 支付成功,交易结束，不可退款
     */
    SUCCESSFINISHED("TRADE_FINISHED"),

    /**
     * 未支付
     */
    NOTPAY("WAIT_BUYER_PAY"),

    /**
     * 已关闭
     */
    CLOSED("TRADE_CLOSED"),

    /**
     * 退款成功
     */
    REFUND_SUCCESS("REFUND_SUCCESS"),

    /**
     * 退款失败
     */
    REFUND_ERROR("REFUND_ERROR");

    /**
     * 类型
     */
    private final String type;
}
