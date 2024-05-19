package com.luojiapay.payment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_payment_info")
public class PaymentInfo extends BaseEntity{

    private String orderNo;//商品订单编号

    private String transactionId;//支付系统交易编号

    private String paymentType;//支付类型

    private String tradeType;//交易类型

    private String tradeState;//交易状态

    private String tradeStateDesc;//交易状态

    private Integer total;//订单总金额(分)

    private Integer payerTotal;//支付金额(分)

    private String content;//通知参数
}
