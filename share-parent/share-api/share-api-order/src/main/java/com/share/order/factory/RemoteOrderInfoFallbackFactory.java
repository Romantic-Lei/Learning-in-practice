package com.share.order.factory;

import com.share.common.core.exception.ServiceException;
import com.share.order.api.RemoteOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 * @author share
 */
@Component
public class RemoteOrderInfoFallbackFactory implements FallbackFactory<RemoteOrderInfoService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteOrderInfoFallbackFactory.class);

    @Override
    public RemoteOrderInfoService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        throw new ServiceException("调用出现错误");
    }
}
