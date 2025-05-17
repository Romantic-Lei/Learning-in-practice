package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.Cabinet;

import java.util.List;

public interface CabinetMapper extends BaseMapper<Cabinet> {

    /**
     * 查询充电宝柜机列表
     *
     * @param cabinet 充电宝柜机
     * @return 充电宝柜机集合
     */
    public List<Cabinet> selectCabinetList(Cabinet cabinet);

}