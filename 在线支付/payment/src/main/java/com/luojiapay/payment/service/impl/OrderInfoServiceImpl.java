package com.luojiapay.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.mapper.OrderInfoMapper;
import com.luojiapay.payment.service.OrderInfoService;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

}
