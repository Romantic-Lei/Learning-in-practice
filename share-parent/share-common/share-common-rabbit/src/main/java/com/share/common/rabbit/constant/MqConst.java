package com.share.common.rabbit.constant;

public class MqConst {

    /**
     * 测试
     */
    public static final String EXCHANGE_TEST = "spzx.test";
    public static final String ROUTING_TEST = "spzx.test";
    public static final String ROUTING_CONFIRM = "spzx.confirm";
    //队列
    public static final String QUEUE_TEST  = "spzx.test";
    public static final String QUEUE_CONFIRM  = "spzx.confirm";


    /**
     * 订单
     */
    public static final String EXCHANGE_ORDER = "share.order";
    public static final String ROUTING_SUBMIT_ORDER = "share.submit.order";
    public static final String ROUTING_END_ORDER = "share.end.order";
    //队列
    public static final String QUEUE_SUBMIT_ORDER = "share.submit.order";
    public static final String QUEUE_END_ORDER = "share.end.order";

    /**
     * 支付
     */
    public static final String EXCHANGE_PAYMENT_PAY = "share.payment";
    public static final String ROUTING_PAYMENT_PAY = "share.payment.pay";
    public static final String QUEUE_PAYMENT_PAY = "share.payment.pay";


    /**
     * 取消订单延迟消息
     */
    public static final String EXCHANGE_DEVICE = "share.device";
    public static final String ROUTING_UNLOCK_SLOT = "share.unlock.slot";
    public static final String QUEUE_UNLOCK_SLOT = "share.unlock.slot";
    public static final Integer CANCEL_UNLOCK_SLOT_DELAY_TIME = 1 * 5;


}
