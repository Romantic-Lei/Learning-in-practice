package com.share.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SubmitOrderVo {

    @Schema(description = "消息编号")
    private String messageNo;

    @Schema(description = "用户Id")
    private Long UserId;

    //送货地址id
    @Schema(description = "充电宝编号")
    private String powerBankNo;

    /** 借用站点id */
    @Schema(description = "借用站点id")
    private Long startStationId;

    /** 借用地点名称 */
    @Schema(description = "借用地点名称")
    private String startStationName;

    /** 借用柜机编号 */
    @Schema(description = "借用柜机编号")
    private String startCabinetNo;

    @Schema(description = "费用规则id")
    private Long feeRuleId;
}
