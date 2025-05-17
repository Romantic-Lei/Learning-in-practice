package com.share.device.controller;

import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.device.domain.CabinetType;
import com.share.device.service.ICabinetTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(name = "柜机类型接口管理")
@RestController
@RequestMapping("/cabinetType")
public class CabinetTypeController extends BaseController
{
    @Autowired
    private ICabinetTypeService cabinetTypeService;

    @Operation(summary = "查询全部柜机类型列表")
    @GetMapping("/getCabinetTypeList")
    public AjaxResult getCabinetTypeList()
    {
        return success(cabinetTypeService.list());
    }

    @Operation(summary = "添加数据")
    @PostMapping()
    public AjaxResult add(@RequestBody CabinetType cabinetType) {
        boolean isSuccess = cabinetTypeService.save(cabinetType);
        return toAjax(isSuccess);
    }

    @Operation(summary = "修改数据")
    @PutMapping()
    public AjaxResult update(@RequestBody CabinetType cabinetType) {
        boolean isSuccess = cabinetTypeService.updateById(cabinetType);
        return toAjax(isSuccess);
    }

    @Operation(summary = "根据id删除单条或多条删除数据")
    @DeleteMapping("{ids}")
    public AjaxResult delete(@PathVariable Long[] ids) {
        boolean isSuccess = cabinetTypeService.removeBatchByIds(Arrays.asList(ids));
        return toAjax(isSuccess);
    }

    @Operation(summary = "根据id查询详情")
    @GetMapping("/{id}")
    public AjaxResult getCabineTypeById(@PathVariable Long id) {
        CabinetType cabinetType = cabinetTypeService.getById(id);
        return success(cabinetType);
    }

    /**
     * 查询柜机类型列表
     */
    @Operation(summary = "分页查询柜机类型列表")
    @GetMapping("/list")
    public TableDataInfo list(CabinetType cabinetType) {
        // 封装分页参数数据
        startPage();
        // 调用 service 查询分页数据
        List<CabinetType> list = cabinetTypeService.selectCabinetTypeList(cabinetType);
        return getDataTable(list);
    }

}