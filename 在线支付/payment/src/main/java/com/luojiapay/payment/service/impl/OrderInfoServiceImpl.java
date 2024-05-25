package com.luojiapay.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.entity.Product;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.mapper.OrderInfoMapper;
import com.luojiapay.payment.mapper.ProductMapper;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderInfoMapper orderInfoMapper;

    @Override
    public OrderInfo createOrderByProductId(Long productId, Long userId, String payType) {
        // 查找已存在但是未支付订单
        OrderInfo orderInfo = getNoPayOrderByProductId(productId, userId);
        if (null != orderInfo)
            return orderInfo;

        // 获取商品信息
        Product product = productMapper.selectById(productId);
        // 生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo()); // 获取订单号
        orderInfo.setProductId(productId);
        orderInfo.setPaymentType(payType);
        orderInfo.setTotalFee(product.getPrice()); // 单位是分
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setUserId(userId);
        // 保存订单信息
        orderInfoMapper.insert(orderInfo);
        return orderInfo;
    }

    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);
        orderInfoMapper.update(orderInfo, new QueryWrapper<OrderInfo>()
                .eq("order_no", orderNo));
    }

    @Override
    public List<OrderInfo> queryOrderByCreateTimeDesc() {
        List<OrderInfo> list = orderInfoMapper.selectList(new QueryWrapper<OrderInfo>()
                .orderByDesc("create_time"));
        return list;
    }

    /**
     * 更新订单状态信息
     * @param outTradeNo
     * @param orderStatus
     */
    @Override
    public void updateStatusByOrderNo(String outTradeNo, OrderStatus orderStatus) {
        log.info("更新订单状态：{}", orderStatus.getType());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(orderStatus.getType());
        orderInfoMapper.update(orderInfo, new QueryWrapper<OrderInfo>()
                .eq("order_no", outTradeNo));
    }

    @Override
    public String getOrderStatus(String outTradeNo) {
        OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>()
                .eq("order_no", outTradeNo));
        if (orderInfo == null) {
            return null;
        }
        return orderInfo.getOrderStatus();
    }

    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {
        return orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>()
                .eq("order_no", orderNo));
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     * @param minutes
     * @return
     */
    @Override
    public List<OrderInfo> getNoPayOrderByDuration(int minutes, String paymentType) {
        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(new QueryWrapper<OrderInfo>()
                .eq("order_status", OrderStatus.NOTPAY.getType())
                .eq("payment_type", paymentType)
                .le("create_time", instant));
        return orderInfos;
    }

    /**
     * 微信支付二维码有效期两小时，此处可以存放订单的二维码，
     * 用户下单未支付再次下单时可以直接拿到第一次申请的二维码
     * @param productId
     * @param userId
     * @return
     */
    private OrderInfo getNoPayOrderByProductId(Long productId, Long userId){
        OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>()
                .eq("product_id", productId)
                .eq("order_status", OrderStatus.NOTPAY.getType())
                .eq("user_id", userId));
        return orderInfo;
    }
}
