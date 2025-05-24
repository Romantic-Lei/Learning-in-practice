package com.share.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.order.domain.OrderInfo;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 *
 * @author atguigu
 * @date 2024-10-25
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo>
{

    //传递过来sql语句，根据sql语句查询数据库得到报表数据
    List<Map<String, Object>> getOrderCount(String sql);
}
