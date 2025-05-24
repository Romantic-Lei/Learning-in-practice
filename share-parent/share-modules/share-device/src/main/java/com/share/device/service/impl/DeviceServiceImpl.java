package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.share.common.core.domain.R;
import com.share.device.domain.Cabinet;
import com.share.device.domain.Station;
import com.share.device.domain.StationLocation;
import com.share.device.service.ICabinetService;
import com.share.device.service.IDeviceService;
import com.share.device.service.IMapService;
import com.share.device.service.IStationService;
import com.share.device.vo.StationVo;
import com.share.rule.api.RemoteFeeRuleService;
import com.share.rule.domain.FeeRule;
import lombok.extern.slf4j.Slf4j;
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

}