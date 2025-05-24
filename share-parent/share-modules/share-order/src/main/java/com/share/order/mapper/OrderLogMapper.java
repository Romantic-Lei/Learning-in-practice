package com.share.order.mapper;

import java.util.List;
import com.share.order.domain.OrderLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 订单操作日志记录Mapper接口
 *
 * @author atguigu
 * @date 2024-10-25
 */
public interface OrderLogMapper extends BaseMapper<OrderLog>
{

    /**
     * 查询订单操作日志记录列表
     *
     * @param orderLog 订单操作日志记录
     * @return 订单操作日志记录集合
     */
    public List<OrderLog> selectOrderLogList(OrderLog orderLog);

}
