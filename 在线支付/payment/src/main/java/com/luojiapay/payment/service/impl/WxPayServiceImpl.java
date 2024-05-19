package com.luojiapay.payment.service.impl;

import com.luojiapay.payment.config.WxPayConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.wxpay.WxNotifyType;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.WxPayService;
import com.luojiapay.payment.util.OrderNoUtils;
import com.wechat.pay.java.core.Config;
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

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    Config config;
    @Autowired
    WxPayConfig wxPayConfig;
    @Autowired
    OrderInfoService orderInfoService;

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
}
