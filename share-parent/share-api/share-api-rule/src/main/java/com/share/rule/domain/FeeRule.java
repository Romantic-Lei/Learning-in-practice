package com.share.rule.domain;

import com.share.common.core.annotation.Excel;
import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 费用规则对象 fee_rule
 *
 * @author atguigu
 * @date 2024-10-25
 */
@Data
@Schema(description = "费用规则")
public class FeeRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则名称 */
    @Excel(name = "规则名称")
    @Schema(description = "规则名称")
    private String name;

    /** 规则代码 */
    @Excel(name = "规则代码")
    @Schema(description = "规则代码")
    private String rule;

    /** 规则描述 */
    @Excel(name = "规则描述")
    @Schema(description = "规则描述")
    private String description;

    /** 状态代码，1有效，2关闭 */
    @Excel(name = "状态代码，1有效，2关闭")
    @Schema(description = "状态代码，1有效，2关闭")
    private String status;

}
