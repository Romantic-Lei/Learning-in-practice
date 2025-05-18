package com.share.device.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.device.domain.PowerBank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class CabinetSlotVo {

    /** 柜机id */
    @Schema(description = "柜机id")
    private Long cabinetId;

    /** 插槽编号 */
    @Schema(description = "插槽编号")
    private String slotNo;

    /** 充电宝id */
    @Schema(description = "充电宝id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)//指定null时不更新该字段
    private Long powerBankId;

    /** 状态（1占用 0空闲 2锁定） */
    @Schema(description = "状态")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private PowerBank powerBank;

}
