package com.share.auth.service;

import com.share.common.core.constant.Constants;
import com.share.common.core.constant.SecurityConstants;
import com.share.common.core.domain.R;
import com.share.common.core.exception.ServiceException;
import com.share.common.core.utils.StringUtils;
import com.share.user.api.RemoteUserService;
import com.share.system.api.model.LoginUser;
import com.share.user.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class H5LoginService {
    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private SysRecordLogService recordLogService;

    /**
     * 登录
     */
    public LoginUser login(String code) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(code)) {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("微信code必须填写");
        }
        // 查询用户信息
        R<UserInfo> userResult = remoteUserService.wxLogin(code);

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new ServiceException("微信授权登录失败");
        }

        if (R.FAIL == userResult.getCode()) {
            throw new ServiceException(userResult.getMsg());
        }

        UserInfo userInfo = userResult.getData();
        LoginUser loginUser = new LoginUser();
        loginUser.setUserid(userInfo.getId());
        loginUser.setUsername(userInfo.getWxOpenId());
        loginUser.setStatus(userInfo.getStatus()+"");
        if ("0".equals(userInfo.getStatus()))
        {
            recordLogService.recordLogininfor(userInfo.getWxOpenId(), Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + userInfo.getWxOpenId() + " 已停用");
        }
        recordLogService.recordLogininfor(userInfo.getWxOpenId(), Constants.LOGIN_SUCCESS, "登录成功");

        //更新登录信息
//        UpdateUserLogin updateUserLogin = new UpdateUserLogin();
//        updateUserLogin.setUserId(userInfo.getId());
//        updateUserLogin.setLastLoginIp(IpUtils.getIpAddr());
//        updateUserLogin.setLastLoginTime(new Date());
//        remoteUserInfoService.updateUserLogin(updateUserLogin, SecurityConstants.INNER);
        return loginUser;
    }
}