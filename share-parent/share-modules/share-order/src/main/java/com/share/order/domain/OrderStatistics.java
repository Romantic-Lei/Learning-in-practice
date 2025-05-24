package com.share.order.domain;

import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics extends BaseEntity {

    @Schema(description = "省code")
    private String provinceCode;

    @Schema(description = "市code")
    private String cityCode;

    @Schema(description = "区code")
    private String districtCode;

    @Schema(description = "站点id")
    private Long stationId;

    @Schema(description = "统计日期")
    private Date orderDate;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "总数量")
    private Integer totalNum;
}
