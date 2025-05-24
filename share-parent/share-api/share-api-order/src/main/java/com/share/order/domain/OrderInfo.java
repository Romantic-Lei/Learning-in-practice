package com.share.order.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.common.core.annotation.Excel;
import com.share.common.core.web.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单对象 order_info
 *
 * @author atguigu
 * @date 2024-10-25
 */
@Data
@Schema(description = "订单")
public class OrderInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户ID")
    @Schema(description = "用户ID")
    private Long userId;

    /** 订单号 */
    @Excel(name = "订单号")
    @Schema(description = "订单号")
    private String orderNo;

    /** 充电宝编号 */
    @Excel(name = "充电宝编号")
    @Schema(description = "充电宝编号")
    private String powerBankNo;

    /** 借用开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "借用开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "借用开始时间")
    private Date startTime;

    /** 借用站点id */
    @Excel(name = "借用站点id")
    @Schema(description = "借用站点id")
    private Long startStationId;

    /** 借用地点名称 */
    @Excel(name = "借用地点名称")
    @Schema(description = "借用地点名称")
    private String startStationName;

    /** 借用柜机编号 */
    @Excel(name = "借用柜机编号")
    @Schema(description = "借用柜机编号")
    private String startCabinetNo;

    /** 归还时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "归还时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "归还时间")
    private Date endTime;

    /** 归还站点id */
    @Excel(name = "归还站点id")
    @Schema(description = "归还站点id")
    private Long endStationId;

    /** 归还地点名称 */
    @Excel(name = "归还地点名称")
    @Schema(description = "归还地点名称")
    private String endStationName;

    /** 归还柜机编号 */
    @Excel(name = "归还柜机编号")
    @Schema(description = "归还柜机编号")
    private String endCabinetNo;

    /** 借用时长 */
    @Excel(name = "借用时长")
    @Schema(description = "借用时长")
    private Integer duration;

    /** 费用规则id */
    @Excel(name = "费用规则id")
    @Schema(description = "费用规则id")
    private Long feeRuleId;

    @Excel(name = "费用规则")
    @Schema(description = "费用规则")
    private String feeRule;

    /** 总金额 */
    @Excel(name = "总金额")
    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    /** 抵扣金额 */
    @Excel(name = "抵扣金额")
    @Schema(description = "抵扣金额")
    private BigDecimal deductAmount;

    /** 实际订单金额 */
    @Excel(name = "实际订单金额")
    @Schema(description = "实际订单金额")
    private BigDecimal realAmount;

    /** 微信付款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "微信付款时间", width = 30, dateFormat = "yyyy-MM-dd")
    @Schema(description = "微信付款时间")
    private Date payTime;

    /** 微信支付订单号 */
    @Excel(name = "微信支付订单号")
    @Schema(description = "微信支付订单号")
    private String transactionId;

    /** 订单状态： */
    @Excel(name = "订单状态：")
    @Schema(description = "订单状态：")
    private String status;


    @Schema(description = "订单账单祥")
    @TableField(exist = false)
    private List<OrderBill> orderBillList;

    @Schema(description = "用户信息")
    @TableField(exist = false)
    private UserInfoVo userInfoVo;
}
