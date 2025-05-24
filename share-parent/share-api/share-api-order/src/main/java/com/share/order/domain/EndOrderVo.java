package com.share.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class EndOrderVo {

    @Schema(description = "消息编号")
    private String messageNo;

    @Schema(description = "归还时间")
    private Date endTime;

    @Schema(description = "归还站点id")
    private Long endStationId;

    @Schema(description = "归还地点名称")
    private String endStationName;

    @Schema(description = "归还柜机编号")
    private String endCabinetNo;

    @Schema(description = "充电宝编号")
    private String powerBankNo;
}
