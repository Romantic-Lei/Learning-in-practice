package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.device.domain.CabinetSlot;
import com.share.device.mapper.CabinetSlotMapper;
import com.share.device.service.ICabinetSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabinetSlotService extends ServiceImpl<CabinetSlotMapper, CabinetSlot> implements ICabinetSlotService {

    @Autowired
    private CabinetSlotMapper cabinetSlotMapper;

    @Override
    public CabinetSlot getBtSlotNo(Long cabinetId, String slotNo) {
        return cabinetSlotMapper.selectOne(new LambdaQueryWrapper<CabinetSlot>()
                .eq(CabinetSlot::getCabinetId, cabinetId)
                .eq(CabinetSlot::getSlotNo, slotNo));
    }
}
