package com.share.rule.api;

import com.share.common.core.domain.R;
import com.share.rule.domain.FeeRule;
import com.share.rule.domain.FeeRuleRequestForm;
import com.share.rule.domain.FeeRuleResponseVo;
import com.share.rule.factory.RemoteFeeRuleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户服务
 *
 * @author share
 */
@FeignClient(contextId = "remoteFeeRuleService",
        value = "share-rule",
        fallbackFactory = RemoteFeeRuleFallbackFactory.class)
public interface RemoteFeeRuleService
{

    @PostMapping("/feeRule/calculateOrderFee")
    public R<FeeRuleResponseVo> calculateOrderFee(@RequestBody FeeRuleRequestForm feeRuleRequestForm);

    @PostMapping(value = "/feeRule/getFeeRuleList")
    public R<List<FeeRule>> getFeeRuleList(@RequestBody List<Long> feeRuleIdList);

    @GetMapping(value = "/feeRule/getFeeRule/{id}")
    public R<FeeRule> getFeeRule(@PathVariable("id") Long id);
}
