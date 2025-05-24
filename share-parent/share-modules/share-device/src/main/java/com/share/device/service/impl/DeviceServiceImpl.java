package com.share.device.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.context.SecurityContextHolder;
import com.share.common.core.domain.R;
import com.share.common.core.exception.ServiceException;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.*;
import com.share.device.emqx.EmqxClientWrapper;
import com.share.device.service.*;
import com.share.device.vo.StationVo;
import com.share.order.api.RemoteOrderInfoService;
import com.share.order.domain.OrderInfo;
import com.share.rule.api.RemoteFeeRuleService;
import com.share.rule.domain.FeeRule;
import com.share.user.api.RemoteUserService;
import com.share.user.domain.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private IStationService stationService;

    @Autowired
    private ICabinetService cabinetService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IMapService mapService;

    @Autowired
    private RemoteFeeRuleService remoteFeeRuleService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteOrderInfoService remoteOrderInfoService;

    @Autowired
    private EmqxClientWrapper emqxClientWrapper;

    @Autowired
    private ICabinetSlotService cabinetSlotService;

    @Autowired
    private IPowerBankService  powerBankService;

    @Override
    public List<StationVo> nearbyStation(String latitude, String longitude, Integer radius) {
        //坐标，确定中心点
        // GeoJsonPoint(double x, double y) x 表示经度，y 表示纬度。
        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(Double.parseDouble(longitude), Double.parseDouble(latitude));
        //画圈的半径,50km范围
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        //画了一个圆圈
        Circle circle = new Circle(geoJsonPoint, distance);
        //条件排除自己
        Query query = Query.query(Criteria.where("location").withinSphere(circle));
        List<StationLocation> stationLocationList = this.mongoTemplate.find(query, StationLocation.class);
        if (CollectionUtils.isEmpty(stationLocationList)) return null;

        //组装数据
        List<Long> stationIdList =stationLocationList.stream().map(StationLocation::getStationId).collect(Collectors.toList());
        //获取站点列表
        List<Station> stationList = stationService.list(new LambdaQueryWrapper<Station>().in(Station::getId, stationIdList).isNotNull(Station::getCabinetId));

        //获取柜机id列表
        List<Long> cabinetIdList = stationList.stream().map(Station::getCabinetId).collect(Collectors.toList());
        //获取柜机id与柜机信息Map
        Map<Long, Cabinet> cabinetIdToCabinetMap = cabinetService.listByIds(cabinetIdList).stream().collect(Collectors.toMap(Cabinet::getId, Cabinet -> Cabinet));

        List<StationVo> stationVoList = new ArrayList<>();
        stationList.forEach(station -> {
            StationVo stationVo = new StationVo();
            BeanUtils.copyProperties(station, stationVo);

            // 计算距离
            Double distanceStation = mapService.calculateDistance(longitude, latitude, station.getLongitude().toString(), station.getLatitude().toString());
            stationVo.setDistance(distanceStation);

            // 获取柜机信息
            Cabinet cabinet = cabinetIdToCabinetMap.get(station.getCabinetId());
            //可用充电宝数量大于0，可借用
            if(cabinet.getAvailableNum() > 0) {
                stationVo.setIsUsable("1");
            } else {
                stationVo.setIsUsable("0");
            }
            // 获取空闲插槽数量大于0，可归还
            if (cabinet.getFreeSlots() > 0) {
                stationVo.setIsReturn("1");
            } else {
                stationVo.setIsReturn("0");
            }
            
            // 获取站点规则数据
            Long feeRuleId = station.getFeeRuleId();
            R<FeeRule> feeRuleResult = remoteFeeRuleService.getFeeRule(feeRuleId);
            FeeRule feeRule = feeRuleResult.getData();
            stationVo.setFeeRule(feeRule.getDescription());

            stationVoList.add(stationVo);
        });
        return stationVoList;
    }

    @Override
    public StationVo getStation(Long id, String latitude, String longitude) {
        Station station = stationService.getById(id);
        StationVo stationVo = new StationVo();
        BeanUtils.copyProperties(station, stationVo);
        // 计算距离
        Double distance = mapService.calculateDistance(longitude, latitude, station.getLongitude().toString(), station.getLatitude().toString());
        stationVo.setDistance(distance);

        // 获取柜机信息
        Cabinet cabinet = cabinetService.getById(station.getCabinetId());
        //可用充电宝数量大于0，可借用
        if(cabinet.getAvailableNum() > 0) {
            stationVo.setIsUsable("1");
        } else {
            stationVo.setIsUsable("0");
        }
        // 获取空闲插槽数量大于0，可归还
        if (cabinet.getFreeSlots() > 0) {
            stationVo.setIsReturn("1");
        } else {
            stationVo.setIsReturn("0");
        }

        // 获取费用规则
        FeeRule feeRule = remoteFeeRuleService.getFeeRule(station.getFeeRuleId()).getData();
        stationVo.setFeeRule(feeRule.getDescription());
        return stationVo;
    }

    @Override
    public ScanChargeVo scanCharge(String cabinetNo) {
        // 扫码充电返回对象
        ScanChargeVo scanChargeVo = new ScanChargeVo();
        // 1.远程调用：根据当前登录用户id查询用户信息
        // 从用户信息获取是否支持免押金充电
        R<UserInfo> userInfoR = remoteUserService.getInfo(SecurityContextHolder.getUserId());
        UserInfo userInfo = userInfoR.getData();
        // 判断
        if (userInfo == null) {
            throw new ServiceException("获取用户信息失败");
        }
        // 判断是否免押金
        if (0  == userInfo.getDepositStatus()) {
            throw new ServiceException("未达到免押金要求");
        }
        
        // 2.远程调用，判断用户是否有未完成订单
        R<OrderInfo> orderInfoR = remoteOrderInfoService.getNoFinishOrder(SecurityContextHolder.getUserId());
        OrderInfo orderInfo = orderInfoR.getData();
        if (orderInfo != null) {
            if("0".equals(orderInfo.getStatus())) {
                scanChargeVo.setStatus("2");
                scanChargeVo.setMessage("有未归还充电宝，请归还后使用");
                return scanChargeVo;
            }
            if("1".equals(orderInfo.getStatus())) {
                scanChargeVo.setStatus("3");
                scanChargeVo.setMessage("有未支付订单，去支付");
                return scanChargeVo;
            }
        }

        // 3.从柜机里面获取最优充电宝
        AvailableProwerBankVo availableProwerBankVo = this.checkAvailableProwerBank(cabinetNo);
        if(!StringUtils.isEmpty(availableProwerBankVo.getErrMessage())) {
            throw new ServiceException(availableProwerBankVo.getErrMessage());
        }

        // 4.使用mqtt 发送消息到设备
        JSONObject object = new JSONObject();
        object.put("uId", SecurityContextHolder.getUserId());
        object.put("mNo", "mm"+ RandomUtil.randomString(8));
        object.put("cNo", cabinetNo);
        object.put("pNo", availableProwerBankVo.getPowerBankNo());
        object.put("sNo", availableProwerBankVo.getSlotNo());
        String topic = String.format(com.share.device.emqx.constant.EmqxConstants.TOPIC_SCAN_SUBMIT, cabinetNo);
        emqxClientWrapper.publish(topic, object.toJSONString());

        // 5.返回封装数据
        scanChargeVo.setStatus("1");
        return scanChargeVo;
    }

    // 获取柜机里面的充电宝信息
    private AvailableProwerBankVo checkAvailableProwerBank(String cabinetNo) {
        // 1.创建 AvailableProwerBankVo 对象
        AvailableProwerBankVo availableProwerBankVo = new AvailableProwerBankVo();
        // 2.查询柜机信息
        Cabinet cabinet = cabinetService.getBtCabineNo(cabinetNo);
        // 3.判断柜机是否为空
        if (cabinet != null) {
            if (cabinet.getAvailableNum() == 0) {
                availableProwerBankVo.setErrMessage("无可用充电宝");
                return availableProwerBankVo;
            }
        } else {
            availableProwerBankVo.setErrMessage("无此柜机");
            return availableProwerBankVo;
        }

        // 4.查询充电宝信息
        List<CabinetSlot> cabinetSlotList = cabinetSlotService.list(new LambdaQueryWrapper<CabinetSlot>()
                .eq(CabinetSlot::getCabinetId, cabinet.getId()));
        // 5.返回插槽列表list集合，获取对应充电宝id集合
        List<Long> powerBankIdList = cabinetSlotList.stream()
                .filter(cabinetSlot -> cabinetSlot.getPowerBankId() != null)
                .map(CabinetSlot::getPowerBankId)
                .toList();
        // 6.根据充电宝id集合查询充电宝信息
        List<PowerBank> powerBankList = powerBankService.list(new LambdaQueryWrapper<PowerBank>()
                .in(PowerBank::getId, powerBankIdList)
                .eq(PowerBank::getStatus, 1));
        if(CollectionUtils.isEmpty(powerBankList)) {
            availableProwerBankVo.setErrMessage("无可用充电宝");
            return availableProwerBankVo;
        }

        // 7.根据电量降序排列
        if(powerBankList.size() > 1) {
            Collections.sort(powerBankList, 
                    (o1, o2) -> o2.getElectricity().compareTo(o1.getElectricity()));
        }
        // 8.获取电量最多的充电宝信息
        PowerBank powerBank = powerBankList.get(0);
        // 9.查找电量最多的充电宝对应的插槽信息
        CabinetSlot cabinetSlot = cabinetSlotList.stream()
                .filter(item -> null != item.getPowerBankId()
                        && item.getPowerBankId().equals(powerBank.getId()))
                .collect(Collectors.toList()).get(0);

        // 10.锁定柜机卡槽
        cabinetSlot.setStatus("2");
        cabinetSlotService.updateById(cabinetSlot);

        // 11.设置返回对象
        availableProwerBankVo.setPowerBankNo(powerBank.getPowerBankNo());
        availableProwerBankVo.setSlotNo(cabinetSlot.getSlotNo());
        return availableProwerBankVo;
    }

}