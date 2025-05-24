package com.share.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.device.domain.Cabinet;
import com.share.device.vo.CabinetAndCabinetSlotVo;

import java.util.List;

public interface ICabinetService extends IService<Cabinet> {

    List<Cabinet> selectCabinetList(Cabinet cabinet);

    int saveCabinet(Cabinet cabinet);

    int updateCabinet(Cabinet cabinet);

    int removeCabinet(List<Long> idList);

    /**
     * 未使用柜机
     * @param keyword
     * @return
     */
    List<Cabinet> searchNoUseList(String keyword);

    CabinetAndCabinetSlotVo getAllInfo(Long id);

    Cabinet getBtCabineNo(String cabinetNo);
    
}