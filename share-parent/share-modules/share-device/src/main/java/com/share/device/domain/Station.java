package com.share.device.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "站点")
public class Station extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

    /** 省code */
    @Schema(description = "省code")
    private String provinceCode;

    /** 市code */
    @Schema(description = "市code")
    private String cityCode;

    /** 区code */
    @Schema(description = "区code")
    private String districtCode;

    /** 详细地址 */
    @Schema(description = "详细地址")
    private String address;

    /** 完整地址 */
    @Schema(description = "完整地址")
    private String fullAddress;

    /** 站点负责人名称 */
    @Schema(description = "站点负责人名称")
    private String headName;

    /** 站点负责人电话 */
    @Schema(description = "站点负责人电话")
    private String headPhone;

    /** 柜机id */
    @Schema(description = "柜机id")
    private Long cabinetId;

    /** 费用规则id */
    @Schema(description = "费用规则id")
    private Long feeRuleId;

    /** 状态（1正常 0停用） */
    @Schema(description = "状态")
    private String status;

    @Schema(description = "柜机编号")
    @TableField(exist = false)
    private String cabinetNo;

    @Schema(description = "费用规则")
    @TableField(exist = false)
    private String feeRuleName;
}