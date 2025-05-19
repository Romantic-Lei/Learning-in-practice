package com.share.rule.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FeeRuleRequestForm {


    @Schema(description = "费用规则id")
    private Long FeeRuleId;

    @Schema(description = "借用时长")
    private Integer duration;

}
