package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.device.service.IRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "地区信息接口管理")
@RestController
@RequestMapping("/region")
public class RegionController extends BaseController {
    @Autowired
    private IRegionService regionService;

    @Operation(summary = "根据上级code获取下级数据列表")
    @GetMapping(value = "/treeSelect/{parentCode}")
    public AjaxResult treeSelect(@PathVariable String parentCode) {
        return success(regionService.treeSelect(parentCode));
    }

}