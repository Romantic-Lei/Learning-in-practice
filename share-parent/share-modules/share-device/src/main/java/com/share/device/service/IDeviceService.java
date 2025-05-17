package com.share.device.service;

import com.share.device.vo.StationVo;

import java.util.List;

public interface IDeviceService {

    List<StationVo> nearbyStation(String latitude, String longitude, Integer radius);

}