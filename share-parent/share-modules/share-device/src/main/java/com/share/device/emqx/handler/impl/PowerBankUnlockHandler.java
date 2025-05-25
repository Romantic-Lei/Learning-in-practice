package com.share.device.emqx.handler.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share.common.rabbit.constant.MqConst;
import com.share.common.rabbit.service.RabbitService;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import com.share.device.domain.PowerBank;
import com.share.device.domain.Station;
import com.share.device.emqx.annotation.JiaEmqx;
import com.share.device.emqx.handler.MessageHandler;
import com.share.device.service.ICabinetService;
import com.share.device.service.ICabinetSlotService;
import com.share.device.service.IPowerBankService;
import com.share.device.service.IStationService;
import com.share.order.domain.SubmitOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@JiaEmqx(topic = com.share.device.emqx.constant.EmqxConstants.TOPIC_POWERBANK_UNLOCK)
public class PowerBankUnlockHandler implements MessageHandler {

    @Autowired
    private ICabinetService cabinetService;

    @Autowired
    private IPowerBankService powerBankService;

    @Autowired
    private ICabinetSlotService cabinetSlotService;

    @Autowired
    private IStationService stationService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handleMessage(JSONObject message) {
        log.info("handleMessage: {}", message.toJSONString());
        // 1.获取messageNo，防止重复提交
        String messageNo = message.getString("messageNo");
        // 基于Redis实现重复提交
        String key = "powerBank:unlock:" + messageNo;
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, messageNo,
                1, TimeUnit.HOURS);
        if(Boolean.FALSE.equals(ifAbsent)) {
            // 失败了，则表示重复提交，这里不做处理
            return;
        }
        // 2.获取柜机编号，充电宝编号、插槽编号
        //柜机编号
        String cabinetNo = message.getString("cNo");
        //充电宝编号
        String powerBankNo = message.getString("pNo");
        //插槽编号
        String slotNo = message.getString("sNo");
        //用户id，非空判断
        Long userId = message.getLong("uId");
        if (StringUtils.isEmpty(cabinetNo)
                || StringUtils.isEmpty(powerBankNo)
                || StringUtils.isEmpty(slotNo)
                || null == userId) {
            log.info("参数为空: {}", message.toJSONString());
            return;
        }

        // 3.根据柜机编号获取柜机信息
        Cabinet cabinet = cabinetService.getBtCabinetNo(cabinetNo);
        // 4.获取充电宝信息根据充电宝编号
        PowerBank powerBank = powerBankService.getByPowerBankNo(powerBankNo);
        // 5.根据柜机Id+插槽编号获取插槽信息
        CabinetSlot cabinetSlot = cabinetSlotService.getBtSlotNo(cabinet.getId(), slotNo);
        // 6.根据柜机id获取站点数据
        Station station = stationService.getOne(new LambdaQueryWrapper<Station>()
                .eq(Station::getCabinetId, cabinet.getId()));
        // 7.更新充电宝信息
        powerBank.setStatus("2");
        powerBankService.updateById(powerBank);
        // 8.更新插槽信息
        cabinetSlot.setStatus("0");
        cabinetSlot.setPowerBankId(null);
        cabinetSlot.setUpdateTime(new Date());
        cabinetSlotService.updateById(cabinetSlot);
        // 9.更新柜机信息
        int freeSlots = cabinet.getFreeSlots() + 1;
        cabinet.setFreeSlots(freeSlots);
        int usedSlots = cabinet.getUsedSlots() - 1;
        cabinet.setUsedSlots(usedSlots);
        //可以借用
        int availableNum = cabinet.getAvailableNum() - 1;
        cabinet.setAvailableNum(availableNum);
        cabinet.setUpdateTime(new Date());
        cabinetService.updateById(cabinet);

        // 10.使用RabbitMQ异步生成订单信息
        SubmitOrderVo submitOrderVo = new SubmitOrderVo();
        submitOrderVo.setMessageNo(messageNo);
        submitOrderVo.setUserId(userId);
        submitOrderVo.setPowerBankNo(powerBankNo);
        submitOrderVo.setStartStationId(station.getId());
        submitOrderVo.setStartStationName(station.getName());
        submitOrderVo.setStartCabinetNo(cabinetNo);
        submitOrderVo.setFeeRuleId(station.getFeeRuleId());
        log.info("构建订单对象: {}", JSONObject.toJSONString(submitOrderVo));
        //发送mq信息
        rabbitService.sendMessage(MqConst.EXCHANGE_ORDER, MqConst.ROUTING_SUBMIT_ORDER, JSONObject.toJSONString(submitOrderVo));

    }
}