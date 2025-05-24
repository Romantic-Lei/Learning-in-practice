package com.share.user.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.domain.UserInfo;

/**
 * 用户Service接口
 *
 * @author atguigu
 * @date 2025-05-18
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    UserInfo wxLogin(String code);

    Boolean updateUserLogin(UpdateUserLogin updateUserLogin);

    Boolean isFreeDeposit();

    //统计2024年每个月注册人数
    //远程调用：统计用户注册数据
    Map<String, Object> getUserCount();
}
