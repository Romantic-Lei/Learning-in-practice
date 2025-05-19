package com.share.rules.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeRuleResponse {

    @Schema(description = "总金额")
    private Double totalAmount;

    @Schema(description = "免费价格")
    private Double freePrice;

    @Schema(description = "免费描述")
    private String freeDescription;

    @Schema(description = "超出免费分钟的价格")
    private Double exceedPrice;

    @Schema(description = "超出免费分钟描述")
    private String exceedDescription;
}
