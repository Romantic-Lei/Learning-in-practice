package com.share.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "结算实体类")
public class TradeVo {

    @Schema(description = "结算总金额")
    private BigDecimal totalAmount;


    @Schema(description = "用户流水号")
    private String tradeNo;

    @Schema(description = "是否立即购买")
    private Boolean isBuy = false;
}
