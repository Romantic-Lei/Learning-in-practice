package com.share.rules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.share.common.security.annotation.RequiresPermissions;
import com.share.rules.service.IFeeRuleService;
import com.share.common.core.web.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 费用规则Controller
 *
 * @author atguigu
 * @date 2024-10-25
 */
@Tag(name = "费用规则接口管理")
@RestController
@RequestMapping("/feeRule")
public class FeeRuleController extends BaseController
{
    @Autowired
    private IFeeRuleService feeRuleService;

}

