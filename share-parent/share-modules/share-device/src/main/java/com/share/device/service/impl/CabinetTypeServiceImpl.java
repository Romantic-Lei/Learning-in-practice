package com.share.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.CabinetType;
import com.share.device.mapper.CabinetTypeMapper;
import com.share.device.service.ICabinetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetTypeServiceImpl extends ServiceImpl<CabinetTypeMapper, CabinetType> implements ICabinetTypeService {

    @Autowired
    private CabinetTypeMapper cabinetTypeMapper;

    /**
     * 查询柜机类型列表
     *
     * @param cabinetType 柜机类型
     * @return 柜机类型
     */
    @Override
    public List<CabinetType> selectCabinetTypeList(CabinetType cabinetType)
    {
        return cabinetTypeMapper.selectCabinetTypeList(cabinetType);
    }

}