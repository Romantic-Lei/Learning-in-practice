package com.share.user.factoey;

import com.share.common.core.exception.ServiceException;
import com.share.user.api.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserFallbackFacotry implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        log.info("用户服务调用失败:{}", cause.getMessage());
        throw new ServiceException("用户服务调用失败");
    }
}
