package com.luojiapay.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.luojiapay.payment.config.AlipayClientConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.service.AliPayService;
import com.luojiapay.payment.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    AlipayClient alipayClient;

    @Transactional
    @Override
    public String tradeCreate(Long productId) {
        try {
            // 生成订单
            OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId, 2L);

            // 调用支付宝支付接口
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();

            // 设置商户门店编号
            model.setStoreId("NJ_001");
            // 设置订单绝对超时时间
            // model.setTimeExpire("2016-12-31 10:05:01");
            // 设置订单标题
            model.setSubject(orderInfo.getTitle());
            // 设置请求来源地址
            model.setRequestFromUrl("https://");
            // 设置产品码 目前电脑支付场景下仅支持 FAST_INSTANT_TRADE_PAY
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            // 设置PC扫码支付的方式
            model.setQrPayMode("1");
            // 设置商户自定义二维码宽度
            model.setQrcodeWidth(100L);
            // 设置请求后页面的集成方式
            model.setIntegrationType("PCWEB");
            BigDecimal divide = new BigDecimal(orderInfo.getTotalFee().toString()).divide(new BigDecimal(100));

            // 设置订单包含的商品列表信息
            List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
            GoodsDetail goodsDetail0 = new GoodsDetail();
            goodsDetail0.setGoodsName(orderInfo.getTitle());
            goodsDetail0.setQuantity(1L);// 商品数量
            goodsDetail0.setPrice(divide.toString());
            goodsDetail0.setGoodsId(productId+"");
            goodsDetail.add(goodsDetail0);
            model.setGoodsDetail(goodsDetail);

            // 设置商户的原始订单号
            model.setMerchantOrderNo("20161008001");
            // 设置商户订单号
            model.setOutTradeNo(orderInfo.getOrderNo());

            // 设置订单总金额, 单位是元
            model.setTotalAmount(divide.toString());

            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            // 如果需要返回GET请求，请使用
            // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            String pageRedirectionData = response.getBody();
            System.out.println(pageRedirectionData);

            if (response.isSuccess()) {
                log.info("调用成功，返回结果：{}", response.getBody());
                return response.getBody();
            } else {
                System.out.println("调用失败");
                // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
                String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
                log.error("调用失败，诊断链接：{}", diagnosisUrl);
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
