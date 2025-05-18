package com.share.device.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.common.core.web.domain.BaseEntity;
import com.share.device.domain.Cabinet;
import com.share.device.domain.CabinetSlot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "充电宝柜机与槽位")
public class CabinetAndCabinetSlotVo {

    private Cabinet cabinet;

    private List<CabinetSlotVo> cabinetSlotList;
}