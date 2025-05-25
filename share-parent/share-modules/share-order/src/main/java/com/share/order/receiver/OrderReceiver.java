package com.share.order.receiver;

import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import com.share.common.core.utils.StringUtils;
import com.share.common.rabbit.constant.MqConst;
import com.share.order.domain.EndOrderVo;
import com.share.order.domain.SubmitOrderVo;
import com.share.order.service.IOrderInfoService;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderReceiver {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    //充电宝插入，接收消息结束订单
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MqConst.EXCHANGE_ORDER,durable = "true"),
            value = @Queue(value = MqConst.QUEUE_END_ORDER, durable = "true"),
            key = MqConst.ROUTING_END_ORDER
    ))
    public void endOrder(String content, Message message , Channel channel) {
        EndOrderVo endOrderVo = JSONObject.parseObject(content, EndOrderVo.class);
        //重复消费
        String messageNo = endOrderVo.getMessageNo();
        String key = "order:endorder:"+messageNo;
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, messageNo, 1, TimeUnit.HOURS);
        if(Boolean.FALSE.equals(ifAbsent)) {
            return;
        }
        try {
            //调用方法，结束订单
            orderInfoService.endOrder(endOrderVo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            redisTemplate.delete(key);
            //消息重新回到队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }

    //充电宝弹出，接收消息生成订单
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MqConst.EXCHANGE_ORDER,durable = "true"),
            value = @Queue(value = MqConst.QUEUE_SUBMIT_ORDER, durable = "true"),
            key = MqConst.ROUTING_SUBMIT_ORDER
    ))
    public void createOrder(String content, Message message , Channel channel) {
        //把content转换对象
        SubmitOrderVo submitOrderVo = JSONObject.parseObject(content, SubmitOrderVo.class);
        //TODO 防止重复请求 setnx实现
        String key = "order:submit:" + submitOrderVo.getMessageNo();
        Boolean isExist = redisTemplate.opsForValue().setIfAbsent(key, submitOrderVo.getMessageNo(), 1, TimeUnit.HOURS);
        if (Boolean.FALSE.equals(isExist)) {
            log.info("重复请求: {}", content);
            return;
        }
        try {
            //调用service方法创建订单
            orderInfoService.saveOrder(submitOrderVo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            log.error("订单服务：订单归还失败，订单编号：{}", submitOrderVo.getMessageNo(), e);
            redisTemplate.delete(key);
            // 消费异常，重新入队
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MqConst.EXCHANGE_PAYMENT_PAY, durable = "true"),
            value = @Queue(value = MqConst.QUEUE_PAYMENT_PAY, durable = "true"),
            key = MqConst.ROUTING_PAYMENT_PAY
    ))
    public void processPaySucess(String orderNo, Message message, Channel channel) {
        //业务处理
        if (StringUtils.isNotEmpty(orderNo)) {
            //更改订单支付状态
            orderInfoService.processPaySucess(orderNo);
        }
        //手动应答
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
