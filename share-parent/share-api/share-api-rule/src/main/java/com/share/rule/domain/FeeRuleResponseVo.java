package com.share.rule.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeRuleResponseVo {

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "免费价格")
    private BigDecimal freePrice;

    @Schema(description = "免费描述")
    private String freeDescription;

    @Schema(description = "超出免费分钟的价格")
    private BigDecimal exceedPrice;

    @Schema(description = "超出免费分钟描述")
    private String exceedDescription;
}
