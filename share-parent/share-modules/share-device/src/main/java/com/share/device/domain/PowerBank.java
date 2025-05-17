package com.share.device.domain;

import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "充电宝")
public class PowerBank extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 充电宝编号 */
    @Schema(description = "充电宝编号")
    private String powerBankNo;

    /** 电量 */
    @Schema(description = "电量")
    private BigDecimal electricity;

    /** 描述 */
    @Schema(description = "描述")
    private String description;

    /** 状态（0:未投放 1：可用 2：已租用 3：充电中 4：故障） */
    @Schema(description = "状态")
    private String status;

}