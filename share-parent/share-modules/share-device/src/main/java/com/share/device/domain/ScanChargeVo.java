package com.share.device.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "扫码充电返回对象")
public class ScanChargeVo
{

    /** 状态（1是 0否） */
    @Schema(description = "状态：1：成功 2：有未归还充电宝 3：有未支付订单")
    private String status;

    @Schema(description = "消息")
    private String message;

}