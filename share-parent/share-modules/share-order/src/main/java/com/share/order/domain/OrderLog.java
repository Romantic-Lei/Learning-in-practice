package com.share.order.domain;

import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.share.common.core.annotation.Excel;

/**
 * 订单操作日志记录对象 order_log
 *
 * @author atguigu
 * @date 2024-10-25
 */
@Data
@Schema(description = "订单操作日志记录")
public class OrderLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    @Excel(name = "订单id")
    @Schema(description = "订单id")
    private Long orderId;

    /** 操作人：用户；系统；后台管理员 */
    @Excel(name = "操作人：用户；系统；后台管理员")
    @Schema(description = "操作人：用户；系统；后台管理员")
    private String operateUser;

    /** 订单状态 */
    @Excel(name = "订单状态")
    @Schema(description = "订单状态")
    private Long processStatus;

    /** 备注 */
    @Excel(name = "备注")
    @Schema(description = "备注")
    private String note;

}
