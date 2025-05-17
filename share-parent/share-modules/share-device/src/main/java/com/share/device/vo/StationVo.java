package com.share.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "站点")
public class StationVo {

    @Schema(description = "站点ID")
    private Long id;

    /** 站点名称 */
    @Schema(description = "站点名称")
    private String name;

    /** 站点图片地址 */
    @Schema(description = "站点图片地址")
    private String imageUrl;

    /** 营业时间 */
    @Schema(description = "营业时间")
    private String businessHours;

    /** 经度 */
    @Schema(description = "经度")
    private BigDecimal longitude;

    /** 纬度 */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /** 完整地址 */
    @Schema(description = "完整地址")
    private String fullAddress;

    /** 状态（1是 0否） */
    @Schema(description = "是否可用")
    private String isUsable;

    /** 状态（1是 0否） */
    @Schema(description = "是否可还")
    private String isReturn;

    /** 距离 */
    @Schema(description = "距离")
    private Double distance;

    @Schema(description = "费用规则")
    private String feeRule;

}