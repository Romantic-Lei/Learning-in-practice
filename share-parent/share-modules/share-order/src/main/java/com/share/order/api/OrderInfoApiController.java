package com.share.order.api;

import com.github.pagehelper.PageHelper;
import com.share.common.core.context.SecurityContextHolder;
import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.core.web.page.TableDataInfo;
import com.share.common.security.annotation.InnerAuth;
import com.share.common.security.annotation.RequiresLogin;
import com.share.common.security.utils.SecurityUtils;
import com.share.order.domain.OrderInfo;
import com.share.order.domain.OrderSqlVo;
import com.share.order.service.IOrderInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 *
 * @author atguigu
 * @date 2024-02-22
 */
@Tag(name = "订单接口管理")
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoApiController extends BaseController
{
    @Autowired
    private IOrderInfoService orderInfoService;

    @Operation(summary = "获取订单详细信息")
    @RequiresLogin
    @GetMapping(value = "/getOrderInfo/{id}")
    public AjaxResult getOrderInfo(@PathVariable("id") Long id)
    {
        return success(orderInfoService.selectOrderInfoById(id));
    }

    @Operation(summary = "获取用户订单分页列表")
    @RequiresLogin
    @GetMapping("/userOrderInfoList/{pageNum}/{pageSize}")
    public TableDataInfo list(@PathVariable Integer pageNum,
                              @PathVariable Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        List<OrderInfo> list =
                orderInfoService.selectOrderListByUserId(SecurityContextHolder.getUserId());
        TableDataInfo dataTable = getDataTable(list);
        return dataTable;
    }

    @Operation(summary = "获取未完成订单")
    @RequiresLogin
    @GetMapping("getNoFinishOrder")
    public AjaxResult getNoFinishOrder() {
        return success(orderInfoService.getNoFinishOrder(SecurityUtils.getUserId()));
    }

    @Operation(summary = "获取未完成订单")
    @GetMapping("getNoFinishOrder/{userId}")
    public R<OrderInfo> getNoFinishOrder(@PathVariable Long userId) {
        return R.ok(orderInfoService.getNoFinishOrder(userId));
    }

    @Operation(summary = "根据订单号获取订单信息")
    @GetMapping("getByOrderNo/{orderNo}")
    public R<OrderInfo> getByOrderNo(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderInfoService.getByOrderNo(orderNo);
        return R.ok(orderInfo);
    }

    //远程调用：订单报表
    //传递过来sql语句，根据sql语句查询数据库得到报表数据
    @PostMapping("/getOrderCount")
    public R getOrderCount(@RequestBody OrderSqlVo orderSqlVo) {
        Map<String,Object> map = orderInfoService.getOrderCount(orderSqlVo.getSql());
        return R.ok(map);
    }
}
