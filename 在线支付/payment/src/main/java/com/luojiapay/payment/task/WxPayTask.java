package com.luojiapay.payment.task;

import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class WxPayTask {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    WxPayService wxPayService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void orderConfirm() {
        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByDuration(5);
        for (OrderInfo orderInfo : orderInfoList) {
            if (StringUtils.hasText(orderInfo.getCodeUrl())) {
                String orderNo = orderInfo.getOrderNo();
                log.warn("超过订单====>{}", orderNo);

                // 核实订单状态，调用微信支付查单接口
                wxPayService.checkOrderStatus(orderNo);
            }
        }
    }
}
