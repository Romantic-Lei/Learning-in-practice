package com.luojiapay.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luojiapay.payment.config.WxPayConfig;
import com.luojiapay.payment.entity.OrderInfo;
import com.luojiapay.payment.entity.RefundInfo;
import com.luojiapay.payment.enums.OrderStatus;
import com.luojiapay.payment.enums.PayType;
import com.luojiapay.payment.enums.wxpay.WxApiType;
import com.luojiapay.payment.enums.wxpay.WxNotifyType;
import com.luojiapay.payment.enums.wxpay.WxTradeState;
import com.luojiapay.payment.mapper.RefundInfoMapper;
import com.luojiapay.payment.service.OrderInfoService;
import com.luojiapay.payment.service.PaymentInfoService;
import com.luojiapay.payment.service.RefundInfoService;
import com.luojiapay.payment.service.WxPayService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.http.DefaultHttpClientBuilder;
import com.wechat.pay.java.core.http.HttpClient;
import com.wechat.pay.java.core.http.okhttp.OkHttpClientAdapter;
import com.wechat.pay.java.core.util.IOUtil;
import com.wechat.pay.java.service.billdownload.BillDownloadService;
import com.wechat.pay.java.service.billdownload.BillDownloadServiceExtension;
import com.wechat.pay.java.service.billdownload.DigestBillEntity;
import com.wechat.pay.java.service.billdownload.model.GetFundFlowBillRequest;
import com.wechat.pay.java.service.billdownload.model.GetTradeBillRequest;
import com.wechat.pay.java.service.billdownload.model.QueryBillEntity;
import com.wechat.pay.java.service.partnerpayments.model.TransactionAmount;
import com.wechat.pay.java.service.partnerpayments.nativepay.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
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
    RefundInfoService refundInfoService;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    RefundInfoMapper refundInfoMapper;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 创建订单，调用 Native 支付接口
     * @param productId
     * @return code_url 和 订单号
     */
    @Override
    public Map<String, Object> nativePay(Long productId) {
        OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId, 1L, PayType.WXPAY.getType());
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

    /**
     * 用户取消订单
     * @param orderNo
     */
    @Override
    public void cancelOrder(String orderNo) {
        closeOrder(orderNo);
        // 更新商户端的订单状态
        orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CANCEL);
    }

    public void closeOrder(String orderNo) {
        // 调用微信支付的关单接口
        NativePayService service = new NativePayService.Builder().config(config).build();
        CloseOrderRequest request = new CloseOrderRequest();
        request.setOutTradeNo(orderNo);
        request.setMchid(wxPayConfig.getMchId());
        service.closeOrder(request);
    }


    @Override
    public com.wechat.pay.java.service.payments.model.Transaction queryOrder(String orderNo) {
        log.info("查询接口调用 ===> {}", orderNo);
        NativePayService service = new NativePayService.Builder().config(config).build();
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(wxPayConfig.getMchId());
        request.setOutTradeNo(orderNo);
        com.wechat.pay.java.service.payments.model.Transaction transaction = service.queryOrderByOutTradeNo(request);
        return transaction;
    }

    @Override
    public void refund(String orderNo, String reason) {
        // 创建退款信息
        RefundInfo refundInfo = refundInfoService.createRefundByOrderNo(orderNo, reason);
        // 调用微信支付的退款接口
        RefundService service = new RefundService.Builder().config(config).build();
        CreateRequest request = new CreateRequest();
        request.setOutTradeNo(orderNo);// 订单编号
        request.setOutRefundNo(refundInfo.getRefundNo());// 退款单编号
        request.setReason(reason);
        request.setNotifyUrl(wxPayConfig.getNotifyDomain().concat(WxNotifyType.REFUND_NOTIFY.getType()));
        AmountReq amount = new AmountReq();
        // 退款金额
        amount.setRefund(refundInfo.getRefund().longValue());
        // 原订单金额
        amount.setTotal(refundInfo.getTotalFee().longValue());
        // 退款币种
        amount.setCurrency("CNY");
        request.setAmount(amount);
        Refund refund = service.create(request);

        // 更新订单信息，正在退款中
        orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.REFUND_PROCESSING);
        // 更新退款单
        refundInfoService.updateRefund(refund);
    }

    /**
     * 根据订单号查询微信支付查单接口，核实订单状态
     * 如果订单已支付，则更新商户端订单状态
     * 如果订单未支付，则调用关单接口关闭订单，并更新商户端订单状态
     * @param orderNo
     */
    @Override
    public void checkOrderStatus(String orderNo) {
        log.info("根据订单号核实订单状态：{}", orderNo);
        com.wechat.pay.java.service.payments.model.Transaction transaction = queryOrder(orderNo);

        // 获取微信支付端订单状态
        if (WxTradeState.SUCCESS.getType().equals(transaction.getTradeState().name())) {
            log.info("核实订单已支付 ===> {}", orderNo);
            // 订单已支付则更新本地订单状态
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);
            Transaction nativeTransaction = new Transaction();
            nativeTransaction.setOutTradeNo(transaction.getOutTradeNo());
            nativeTransaction.setTransactionId(transaction.getTransactionId());
            
            // nativeTransaction.setTradeType();
            // nativeTransaction.setTradeState();
            nativeTransaction.setTradeStateDesc(transaction.getTradeStateDesc());
            TransactionAmount amount = new TransactionAmount();
            amount.setTotal(transaction.getAmount().getTotal());
            amount.setPayerTotal(transaction.getAmount().getPayerTotal());
            nativeTransaction.setAmount(amount);
            // 支付记录日志创建
            paymentInfoService.createPaymentInfo(nativeTransaction);
        } else if (WxTradeState.NOTPAY.getType().equals(transaction.getTradeState().name())) {
            closeOrder(orderNo);
            orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.CLOSED);
        }
    }

    @Override
    public Refund queryReFund(String refundNo) {
        RefundService service = new RefundService.Builder().config(config).build();
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(refundNo);
        Refund refund = service.queryByOutRefundNo(request);
        return refund;
    }

    @Override
    public void processRefund(RefundNotification transaction) {
        // 订单号
        String outTradeNo = transaction.getOutTradeNo();

        // 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，
        // 以避免函数重入造成的数据混乱
        // 处理重复通知,获取锁成功则返回true，否则返回false
        if (lock.tryLock()) {
            try {
                String orderStatus = orderInfoService.getOrderStatus(outTradeNo);
                if (!OrderStatus.REFUND_PROCESSING.getType().equals(orderStatus)) {
                    return;
                }

                // 更新订单状态
                orderInfoService.updateStatusByOrderNo(outTradeNo, OrderStatus.REFUND_SUCCESS);
                // 记录支付日志
                RefundInfo refundInfo = new RefundInfo();
                refundInfo.setRefundId(transaction.getRefundId());// 微信支付退款单号
                refundInfo.setRefundStatus(transaction.getRefundStatus().name());// 退款状态
                refundInfo.setContentNotify(transaction.toString());

                refundInfoMapper.update(refundInfo, new QueryWrapper<RefundInfo>()
                        .eq("refund_no", transaction.getOutRefundNo()));
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public String queryBill(String billDate, String type) {
        BillDownloadService service = new BillDownloadService.Builder().config(config).build();
        QueryBillEntity tradeBill = new QueryBillEntity();
        //申请交易账单API
        if ("tradebill".equals(type)) {
            GetTradeBillRequest request = new GetTradeBillRequest();
            request.setBillDate(billDate);
            tradeBill = service.getTradeBill(request);
        } else if ("fundflowbill".equals(type)) {
            // 申请资金账单API
            GetFundFlowBillRequest request = new GetFundFlowBillRequest();
            request.setBillDate(billDate);
            tradeBill = service.getFundFlowBill(request);
        }
        String downloadUrl = tradeBill.getDownloadUrl();
        return downloadUrl;
    }

    @Override
    public String downloadbill(String billDate, String type) throws IOException {
        // 下载账单方式一：
        // 获取账单url地址
        String downloadUrl = queryBill(billDate, type);
        HttpClient httpClient = new DefaultHttpClientBuilder().config(config).build();
        InputStream inputStream = httpClient.download(downloadUrl);
        // 非压缩的账单可使用 core.util.IOUtil 从流读入内存字符串，大账单请慎用
        String respBody = IOUtil.toString(inputStream);
        inputStream.close();
        return respBody;

        // 下载账单方式二：
        // BillDownloadServiceExtension service = new BillDownloadServiceExtension.Builder().config(config).build();
        // //下载交易账单API
        // if ("tradebill".equals(type)) {
        //     GetTradeBillRequest request = new GetTradeBillRequest();
        //     request.setBillDate(billDate);
        //     return IOUtil.toString(service.getTradeBill(request).getInputStream());
        // } else if ("fundflowbill".equals(type)) {
        //     // 下载资金账单API
        //     GetFundFlowBillRequest request = new GetFundFlowBillRequest();
        //     request.setBillDate(billDate);
        //     return IOUtil.toString(service.getFundFlowBill(request).getInputStream());
        // }
        // return "";
    }

    @Override
    public void checkRefundStatus(String refundNo) {
        Refund refund = queryReFund(refundNo);
        if (null != refund) {
            RefundInfo refundInfo = new RefundInfo();
            refundInfo.setRefundId(refund.getRefundId());// 微信支付退款单号
            refundInfo.setRefundStatus(refund.getStatus().name());// 退款状态
            refundInfo.setContentReturn(JSON.toJSONString(refund));

            refundInfoMapper.update(refundInfo, new QueryWrapper<RefundInfo>()
                    .eq("refund_no", refundNo));
            // 维护订单信息
            orderInfoService.updateStatusByOrderNo(refund.getOutTradeNo(), OrderStatus.REFUND_SUCCESS);
        }
    }
}
