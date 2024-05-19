package com.luojiapay.payment.service.impl;

import com.luojiapay.payment.config.WxPayConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.wxpay.WxNotifyType;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.PaymentInfoService;
import com.luojiapay.payment.service.WxPayService;
import com.luojiapay.payment.util.OrderNoUtils;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    Config config;
    @Autowired
    WxPayConfig wxPayConfig;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    PaymentInfoService paymentInfoService;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 创建订单，调用 Native 支付接口
     * @param productId
     * @return code_url 和 订单号
     */
    @Override
    public Map<String, Object> nativePay(Long productId) {
        OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId, 1l);
        if (null != orderInfo && StringUtils.hasText(orderInfo.getCodeUrl())) {
            log.info("订单已存在，二维码已保存");
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("codeUrl", orderInfo.getCodeUrl());
            returnMap.put("orderNo", orderInfo.getOrderNo());
            return returnMap;
        }

        // 构建service
        NativePayService service = new NativePayService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(1);
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setAppid(wxPayConfig.getAppid());
        request.setMchid(wxPayConfig.getMchId());
        request.setDescription(orderInfo.getTitle());
        request.setNotifyUrl(wxPayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));
        request.setOutTradeNo(orderInfo.getOrderNo());
        // 调用下单方法，得到应答
        PrepayResponse response = service.prepay(request);
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        String codeUrl = response.getCodeUrl();
        log.info("code_url:{}", codeUrl);

        // 保存二维码信息
        orderInfoService.saveCodeUrl(orderInfo.getOrderNo(), codeUrl);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("codeUrl", codeUrl);
        returnMap.put("orderNo", orderInfo.getOrderNo());
        return returnMap;
    }

    @Override
    public void processOrder(Transaction transaction) {
        // 订单号
        String outTradeNo = transaction.getOutTradeNo();

        // 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，
        // 以避免函数重入造成的数据混乱
        // 处理重复通知,获取锁成功则返回true，否则返回false
        if (lock.tryLock()) {
            try {
                String orderStatus = orderInfoService.getOrderStatus(outTradeNo);
                if (!OrderStatus.NOTPAY.getType().equals(orderStatus)) {
                    return;
                }

                // 更新订单状态
                orderInfoService.updateStatusByOrderNo(outTradeNo, OrderStatus.SUCCESS);
                // 记录支付日志
                paymentInfoService.createPaymentInfo(transaction);
            } finally {
                lock.unlock();
            }
        }

    }
}
