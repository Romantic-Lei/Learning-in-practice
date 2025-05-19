package com.share.rules.api;

import com.share.common.core.domain.R;
import com.share.rule.domain.FeeRule;
import com.share.rule.domain.FeeRuleRequestForm;
import com.share.rule.domain.FeeRuleResponseVo;
import com.share.rules.domain.vo.FeeRuleResponse;
import com.share.rules.service.IFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feeRule")
@SuppressWarnings({"unchecked", "rawtypes"})
public class FeeRuleApiController {

	@Autowired
	private IFeeRuleService feeRuleService;

	//计算订单费用
	@PostMapping("/calculateOrderFee")
	public R<FeeRuleResponseVo> calculateOrderFee(@RequestBody FeeRuleRequestForm feeRuleRequestForm) {
		FeeRuleResponseVo feeRuleResponseVo =
				feeRuleService.calculateOrderFee(feeRuleRequestForm);
		return R.ok(feeRuleResponseVo);
	}

	//批量获取规则数据列表
	@Operation(summary = "批量获取费用规则信息")
	@PostMapping(value = "/getFeeRuleList")
	public R<List<FeeRule>> getFeeRuleList(@RequestBody List<Long> feeRuleIds) {
		List<FeeRule> feeRuleList = feeRuleService.listByIds(feeRuleIds);
		return R.ok(feeRuleList);
	}

	//根据id获取规则详情
	@Operation(summary = "获取费用规则详细信息")
	@GetMapping(value = "/getFeeRule/{id}")
	public R<FeeRule> getFeeRule(@PathVariable Long id) {
		FeeRule feeRule = feeRuleService.getById(id);
		return R.ok(feeRule);
	}
}

