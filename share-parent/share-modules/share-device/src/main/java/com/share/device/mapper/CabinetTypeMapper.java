package com.share.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.device.domain.CabinetType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface CabinetTypeMapper extends BaseMapper<CabinetType> {

    /**
     * 查询柜机类型列表
     *
     * @param cabinetType 柜机类型
     * @return 柜机类型集合
     */
    public List<CabinetType> selectCabinetTypeList(CabinetType cabinetType);

}