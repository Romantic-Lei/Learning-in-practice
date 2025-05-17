package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.Station;

import java.util.List;

public interface StationMapper extends BaseMapper<Station> {
    public List<Station> selectStationList(Station station);

}