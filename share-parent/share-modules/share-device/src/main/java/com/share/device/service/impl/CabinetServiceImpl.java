package com.share.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.common.core.exception.ServiceException;
import com.share.common.security.utils.SecurityUtils;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import com.share.device.domain.CabinetType;
import com.share.device.domain.PowerBank;
import com.share.device.mapper.CabinetMapper;
import com.share.device.mapper.CabinetSlotMapper;
import com.share.device.mapper.CabinetTypeMapper;
import com.share.device.service.ICabinetService;
import com.share.device.service.IPowerBankService;
import com.share.device.vo.CabinetAndCabinetSlotVo;
import com.share.device.vo.CabinetSlotVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CabinetServiceImpl extends ServiceImpl<CabinetMapper, Cabinet> implements ICabinetService {
    @Autowired
    private CabinetMapper cabinetMapper;
    @Autowired
    private CabinetTypeMapper cabinetTypeMapper;
    @Autowired
    private CabinetSlotMapper cabinetSlotMapper;
    @Autowired
    private IPowerBankService iPowerBankService;

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

    @Override
    public CabinetAndCabinetSlotVo getAllInfo(Long id) {
        Cabinet cabinet = cabinetMapper.selectById(id);
        if (null == cabinet)
            return new CabinetAndCabinetSlotVo();

        CabinetAndCabinetSlotVo cabinetAndCabinetSlotVo = new CabinetAndCabinetSlotVo();
        cabinetAndCabinetSlotVo.setCabinet(cabinet);

        // 柜机id查询柜机插槽信息
        List<CabinetSlot> cabinetSlots = cabinetSlotMapper.selectList(new LambdaQueryWrapper<CabinetSlot>()
                .eq(CabinetSlot::getCabinetId, id)
                .eq(CabinetSlot::getDelFlag, 0));
        if (CollectionUtils.isEmpty(cabinetSlots))
            return cabinetAndCabinetSlotVo;

        List<Long> collect = cabinetSlots.stream().map(CabinetSlot::getPowerBankId).collect(Collectors.toList());
        Map<Long, PowerBank> powerBankMap = iPowerBankService.list(new LambdaQueryWrapper<PowerBank>().in(PowerBank::getId, collect))
                .stream().collect(Collectors.toMap(PowerBank::getId, p -> p));

        List<CabinetSlotVo> cabinetSlotVoList = new ArrayList<>();
        for (CabinetSlot cabinetSlot : cabinetSlots) {
            CabinetSlotVo cabinetSlotVo = new CabinetSlotVo();
            PowerBank powerBank = powerBankMap.get(cabinetSlot.getPowerBankId());
            BeanUtils.copyProperties(cabinetSlot, cabinetSlotVo);
            cabinetSlotVo.setPowerBank(powerBank);
            cabinetSlotVoList.add(cabinetSlotVo);
        }

        cabinetAndCabinetSlotVo.setCabinetSlotList(cabinetSlotVoList);
        return cabinetAndCabinetSlotVo;
    }
}