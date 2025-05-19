package com.share.rules.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FeeRuleRequest {

    @Schema(description = "借用时长")
    private Integer durations;

    @Schema(description = "超出免费时长的小时数")
    private Integer exceedHours;
}
