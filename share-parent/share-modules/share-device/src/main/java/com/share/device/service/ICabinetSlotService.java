package com.share.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.device.domain.CabinetSlot;

public interface ICabinetSlotService extends IService<CabinetSlot> {

    CabinetSlot getBtSlotNo(Long cabinetId, String slotNo);
}
