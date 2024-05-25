package com.luojiapay.payment.task;

import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class AliPayTask {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    AliPayService aliPayService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void orderConfirm() {
        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByDuration(5, PayType.ALIPAY.getType());
        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("超过订单====>{}", orderNo);

            // 核实订单状态，调用微信支付查单接口
            aliPayService.checkOrderStatus(orderNo);
        }
    }
}
