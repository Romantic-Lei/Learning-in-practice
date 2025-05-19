package com.share.user.api;

import com.share.common.core.constant.ServiceNameConstants;
import com.share.common.core.domain.R;
import com.share.user.domain.UserInfo;
import com.share.user.factoey.RemoteUserFallbackFacotry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "remoteUserInfoService",
        value = ServiceNameConstants.SHARE_USER,
        fallbackFactory = RemoteUserFallbackFacotry.class)
public interface RemoteUserService {

    @GetMapping("/userInfo/wxLogin/{code}")
    public R<UserInfo> wxLogin(@PathVariable("code") String code);

}
