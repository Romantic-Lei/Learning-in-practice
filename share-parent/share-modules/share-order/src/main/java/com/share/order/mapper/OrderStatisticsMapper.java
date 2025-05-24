package com.share.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.share.order.domain.OrderRegionStatisticsVo;
import com.share.order.domain.OrderStatistics;
import com.share.order.domain.OrderStatisticsQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics> {

    List<OrderStatistics> selectList(@Param("vo") OrderStatisticsQuery orderStatisticsQuery);

    List<OrderRegionStatisticsVo> selectRegionList(@Param("vo") OrderStatisticsQuery orderStatisticsQuery);
}
