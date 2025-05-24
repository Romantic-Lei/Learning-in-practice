package com.share.device.service;

import com.share.device.domain.ScanChargeVo;
import com.share.device.vo.StationVo;

import java.util.List;

public interface IDeviceService {

    List<StationVo> nearbyStation(String latitude, String longitude, Integer radius);

    StationVo getStation(Long id, String latitude, String longitude);

    // 扫码充电接口
    ScanChargeVo scanCharge(String cabinetNo);

}