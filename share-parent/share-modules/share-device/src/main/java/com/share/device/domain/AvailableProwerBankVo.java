package com.share.device.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AvailableProwerBankVo {

    @Schema(description = "插槽编号")
    private String slotNo;

    @Schema(description = "充电宝编号")
    private String powerBankNo;

    @Schema(description = "错误提示信息")
    private String errMessage;
}
