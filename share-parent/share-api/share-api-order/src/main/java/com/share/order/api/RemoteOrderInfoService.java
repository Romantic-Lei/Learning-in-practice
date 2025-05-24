package com.share.order.api;

import com.share.common.core.domain.R;
import com.share.order.domain.OrderInfo;
import com.share.order.domain.OrderSqlVo;
import com.share.order.factory.RemoteOrderInfoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 用户服务
 *
 * @author share
 */
@FeignClient(contextId = "remoteOrderInfoService",
        value = "share-order",
        fallbackFactory = RemoteOrderInfoFallbackFactory.class)
public interface RemoteOrderInfoService {
    @GetMapping("/orderInfo/getNoFinishOrder/{userId}")
    public R<OrderInfo> getNoFinishOrder(@PathVariable("userId") Long userId);

    @GetMapping("/orderInfo/getByOrderNo/{orderNo}")
    public R<OrderInfo> getByOrderNo(@PathVariable("orderNo") String orderNo);

    //传递过来sql语句，根据sql语句查询数据库得到报表数据
    @PostMapping("/orderInfo/getOrderCount")
    public R getOrderCount(@RequestBody OrderSqlVo orderSqlVo);
}
