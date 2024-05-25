package com.luojiapay.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.entity.RefundInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.wxpay.WxRefundStatus;
import com.luojiapay.payment.mapper.RefundInfoMapper;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.RefundInfoService;
import com.luojiapay.payment.util.OrderNoUtils;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 创建退款单，一笔订单可能有多笔退款，目前这里不考虑此情况
     * @param orderNo
     * @param reason
     * @return
     */
    @Override
    public RefundInfo createRefundByOrderNo(String orderNo, String reason) {
        OrderInfo orderInfo = orderInfoService.getOrderByOrderNo(orderNo);
        if (null == orderInfo)
            return null;

        // 根据订单号生成退款订单
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setOrderNo(orderNo);
        refundInfo.setRefundNo(OrderNoUtils.getRefundNo());// 退款单号
        refundInfo.setTotalFee(orderInfo.getTotalFee());// 原订单金额
        refundInfo.setRefund(orderInfo.getTotalFee());// 退款金额
        refundInfo.setReason(reason);// 退款原因

        // 保存退款订单
        baseMapper.insert(refundInfo);

        return refundInfo;
    }

    @Override
    public void updateRefund(Refund refund) {
        log.info("更新退款单信息");
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setRefundId(refund.getRefundId());// 微信支付退款单号
        refundInfo.setRefundStatus(refund.getStatus().name());// 退款状态
        refundInfo.setContentNotify(refund.toString());

        baseMapper.update(refundInfo, new QueryWrapper<RefundInfo>()
                .eq("refund_no", refund.getOutRefundNo()));
    }

    @Override
    public List<RefundInfo> getNoRefundOrderByDuration(int minutes) {
        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));
        List<RefundInfo> refundInfos = baseMapper.selectList(new QueryWrapper<RefundInfo>()
                .eq("refund_status", WxRefundStatus.PROCESSING.getType())
                .le("create_time", instant));
        return refundInfos;
    }

    @Override
    public void updateRefundForAliPay(String refundNo, String body, String refundStatus) {
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setContentReturn(JSON.toJSONString(body));
        refundInfo.setRefundStatus(refundStatus);

        baseMapper.update(refundInfo, new QueryWrapper<RefundInfo>()
                .eq("refund_no", refundNo));
    }

    @Override
    public RefundInfo queryRefundInfoByOrderNo(String orderNo) {
        List<RefundInfo> refundInfos = baseMapper.selectList(new QueryWrapper<RefundInfo>()
                .eq("order_no", orderNo));
        if (CollectionUtils.isEmpty(refundInfos))
            return null;
        return refundInfos.get(0);
    }
}
