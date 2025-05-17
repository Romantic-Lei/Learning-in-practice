package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.common.core.exception.ServiceException;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import com.share.device.domain.CabinetType;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.CabinetSlotMapper;
import com.share.device.mapper.CabinetTypeMapper;
import com.share.device.service.ICabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CabinetServiceImpl extends ServiceImpl<CabinetMapper, Cabinet> implements ICabinetService {
    @Autowired
    private CabinetMapper cabinetMapper;
    @Autowired
    private CabinetTypeMapper cabinetTypeMapper;
    @Autowired
    private CabinetSlotMapper cabinetSlotMapper;

    @Override
    public List<Cabinet> selectCabinetList(Cabinet cabinet) {
        return cabinetMapper.selectCabinetList(cabinet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveCabinet(Cabinet cabinet) {
        long count = this.count(new LambdaQueryWrapper<Cabinet>().eq(Cabinet::getCabinetNo, cabinet.getCabinetNo()));
        if (count > 0) {
            throw new ServiceException("该柜机编号已存在");
        }
        // 根据柜机类型id查询柜机类型
        CabinetType cabinetType = cabinetTypeMapper.selectById(cabinet.getCabinetTypeId());

        // 设置总插槽数量和可用插槽数量
        cabinet.setTotalSlots(cabinetType.getTotalSlots());
        cabinet.setFreeSlots(cabinetType.getTotalSlots());
        cabinet.setUsedSlots(0);
        cabinet.setAvailableNum(0);
        cabinet.setCreateTime(new Date());
        cabinet.setCreateBy(SecurityUtils.getUsername());
        this.save(cabinet);

        int size = cabinetType.getTotalSlots();
        for(int i=0; i<size; i++) {
            CabinetSlot cabinetSlot = new CabinetSlot();
            cabinetSlot.setCabinetId(cabinet.getId());
            cabinetSlot.setSlotNo(i+1+"");
            cabinetSlot.setCreateTime(new Date());
            cabinet.setCreateBy(SecurityUtils.getUsername());
            cabinetSlotMapper.insert(cabinetSlot);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateCabinet(Cabinet cabinet) {
        // 获取旧的数据
        Cabinet oldCabinet = this.getById(cabinet.getId());
        if (null != oldCabinet && !"0".equals(oldCabinet.getStatus())) {
            throw new ServiceException("该柜机已投放，无法修改");
        }
        //判断柜机编号是否改变
        if (!oldCabinet.getCabinetNo().equals(cabinet.getCabinetNo())) {
            long count = this.count(new LambdaQueryWrapper<Cabinet>().eq(Cabinet::getCabinetNo, cabinet.getCabinetNo()));
            if (count > 0) {
                throw new ServiceException("该柜机编号已存在");
            }
        }

        // 判断是否修改了柜机类型
        if(oldCabinet.getCabinetTypeId().longValue() != cabinet.getCabinetTypeId()) {
            // 根据柜机类型id查询柜机类型
            CabinetType cabinetType = cabinetTypeMapper.selectById(cabinet.getCabinetTypeId());

            // 设置总插槽数量和可用插槽数量
            cabinet.setTotalSlots(cabinetType.getTotalSlots());
            cabinet.setTotalSlots(cabinetType.getTotalSlots());
            cabinet.setFreeSlots(cabinetType.getTotalSlots());
            cabinet.setUsedSlots(0);
            cabinet.setAvailableNum(0);
            cabinet.setUpdateTime(new Date());
            cabinet.setUpdateBy(SecurityUtils.getUsername());
            this.updateById(cabinet);

            // 删除所有插槽
            cabinetSlotMapper.delete(new LambdaQueryWrapper<CabinetSlot>().eq(CabinetSlot::getCabinetId, cabinet.getId()));
            int size = cabinetType.getTotalSlots();
            for(int i=0; i<size; i++) {
                CabinetSlot cabinetSlot = new CabinetSlot();
                cabinetSlot.setCabinetId(cabinet.getId());
                cabinetSlot.setSlotNo(i+1+"");
                cabinetSlot.setCreateTime(new Date());
                cabinet.setCreateBy(SecurityUtils.getUsername());
                cabinetSlotMapper.insert(cabinetSlot);
            }
        } else {
            cabinet.setUpdateTime(new Date());
            cabinet.setUpdateBy(SecurityUtils.getUsername());
            this.updateById(cabinet);
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int removeCabinet(List<Long> idList) {
        this.removeBatchByIds(idList);
        cabinetSlotMapper.delete(new LambdaQueryWrapper<CabinetSlot>().in(CabinetSlot::getCabinetId, idList));
        return 1;
    }

    @Override
    public List<Cabinet> searchNoUseList(String keyword) {
        return cabinetMapper.selectList(new LambdaQueryWrapper<Cabinet>()
                .like(Cabinet::getCabinetNo, keyword)
                .eq(Cabinet::getStatus, "0")
        );
    }
}