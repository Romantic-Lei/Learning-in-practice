package com.luojiapay.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luojiapay.payment.entity.OrderInfo;

public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrderByProductId(Long productId, Long userId);

    void saveCodeUrl(String orderNo, String codeUrl);
}
