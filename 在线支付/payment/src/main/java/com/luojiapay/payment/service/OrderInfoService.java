package com.luojiapay.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrderByProductId(Long productId, Long userId, String payType);

    void saveCodeUrl(String orderNo, String codeUrl);

    List<OrderInfo> queryOrderByCreateTimeDesc();

    void updateStatusByOrderNo(String outTradeNo, OrderStatus orderStatus);

    String getOrderStatus(String outTradeNo);

    OrderInfo getOrderByOrderNo(String orderNo);

    List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType);
}
