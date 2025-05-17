package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.PowerBank;

import java.util.List;

/**
 * 充电宝Mapper接口
 *
 * @author atguigu
 * @date 2024-10-22
 */
public interface PowerBankMapper extends BaseMapper<PowerBank> {

    /**
     * 查询充电宝列表
     *
     * @param powerBank 充电宝
     * @return 充电宝集合
     */
    public List<PowerBank> selectPowerBankList(PowerBank powerBank);

}