package com.share.user.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.domain.UserInfo;

/**
 * 用户Service接口
 *
 * @author atguigu
 * @date 2025-05-18
 */
public interface IUserInfoService extends IService<UserInfo>
{

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    UserInfo wxLogin(String code);

    Boolean updateUserLogin(UpdateUserLogin updateUserLogin);

}
