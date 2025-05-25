package com.share.device.emqx.handler.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share.common.core.constant.DeviceConstants;
import com.share.common.rabbit.constant.MqConst;
import com.share.common.rabbit.service.RabbitService;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import com.share.device.domain.PowerBank;
import com.share.device.domain.Station;
import com.share.device.emqx.annotation.JiaEmqx;
import com.share.device.emqx.constant.EmqxConstants;
import com.share.device.emqx.handler.MessageHandler;
import com.share.device.service.ICabinetService;
import com.share.device.service.ICabinetSlotService;
import com.share.device.service.IPowerBankService;
import com.share.device.service.IStationService;
import com.share.order.domain.EndOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@JiaEmqx(topic = EmqxConstants.TOPIC_POWERBANK_CONNECTED)
public class PowerBankConnectedHandler implements MessageHandler {

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


    @Override
    public void handleMessage(JSONObject message) {
        log.info("handleMessage: {}", message.toJSONString());
        // 1. 获取messageNo，防止重复提交
        String messageNo = message.getString("mNo");
        //防止重复请求
        String key = "property:post:" + messageNo;
        boolean isExist = redisTemplate.opsForValue().setIfAbsent(key, messageNo, 1, TimeUnit.HOURS);
        if (!isExist) {
            log.info("重复请求: {}", message.toJSONString());
            return;
        }

        // 2.获取cabinetNo, powerBanckNo, slotNo, electricity
        //柜机编号
        String cabinetNo = message.getString("cNo");
        //充电宝编号
        String powerBankNo = message.getString("pNo");
        //插槽编号
        String slotNo = message.getString("sNo");
        //当前电量
        BigDecimal electricity = message.getBigDecimal("ety");
        // 3.非空判断
        if (StringUtils.isEmpty(cabinetNo)
                || StringUtils.isEmpty(powerBankNo)
                || StringUtils.isEmpty(slotNo)
                || null == electricity) {
            log.info("参数为空: {}", message.toJSONString());
            return;
        }

        // 4.获取柜机、充电宝、插槽数据
        Cabinet cabinet = cabinetService.getBtCabinetNo(cabinetNo);
        // 获取充电宝
        PowerBank powerBank =powerBankService.getByPowerBankNo(powerBankNo);
        // 获取插槽
        CabinetSlot cabinetSlot = cabinetSlotService.getBtSlotNo(cabinet.getId(), slotNo);

        // 5.更新充电宝信息
        // 状态（0:未投放 1：可用 2：已租用 3：充电中 4：故障）
        //更新充电宝电量与状态
        powerBank.setElectricity(electricity);
        //电量大于可用最低值
        // 状态（0:未投放 1：可用 2：已租用 3：充电中 4：故障）
        if(electricity.subtract(DeviceConstants.ELECTRICITY_MIN).doubleValue() > 0) {
            //可以借用
            powerBank.setStatus("1");
        } else {
            //充电中
            powerBank.setStatus("3");
        }
        powerBankService.updateById(powerBank);
        // 6.更新插槽、柜机信息
        //更新插槽状态
        cabinetSlot.setPowerBankId(powerBank.getId());
        cabinetSlot.setStatus("1");
        cabinetSlot.setUpdateTime(new Date());
        cabinetSlotService.updateById(cabinetSlot);

        //更新柜机信息
        int freeSlots = cabinet.getFreeSlots() - 1;
        cabinet.setFreeSlots(freeSlots);
        int usedSlots = cabinet.getUsedSlots() + 1;
        cabinet.setUsedSlots(usedSlots);
        //可以借用
        if ("1".equals(powerBank.getStatus())) {
            int availableNum = cabinet.getAvailableNum() + 1;
            cabinet.setAvailableNum(availableNum);
        }
        cabinet.setUpdateTime(new Date());
        cabinetService.updateById(cabinet);

        // 获取站点
        // 7.根据柜机id获取站点数据
        Station station = stationService.getOne(new LambdaQueryWrapper<Station>()
                .eq(Station::getCabinetId, cabinet.getId()));
        // 构建结束订单对象
        EndOrderVo endOrderVo = new EndOrderVo();
        endOrderVo.setMessageNo(messageNo);
        endOrderVo.setEndTime(new Date());
        endOrderVo.setEndCabinetNo(cabinetNo);
        endOrderVo.setEndStationId(station.getId());
        endOrderVo.setEndStationName(station.getName());
        endOrderVo.setPowerBankNo(powerBankNo);
        log.info("构建结束订单对象: {}", JSONObject.toJSONString(endOrderVo));

        // 8.使用RabbitMQ异步生成订单信息
        rabbitService.sendMessage(MqConst.EXCHANGE_ORDER,
                MqConst.ROUTING_END_ORDER,
                JSONObject.toJSONString(endOrderVo));
    }
}