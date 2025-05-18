package com.share.user.service.impl;

import java.util.List;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.domain.UserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.share.user.mapper.UserInfoMapper;
import com.share.user.service.IUserInfoService;

/**
 * 用户Service业务层处理
 *
 * @author atguigu
 * @date 2025-05-18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private WxMaService wxMaService;

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户
     */
    @Override
    public List<UserInfo> selectUserInfoList(UserInfo userInfo) {
        return userInfoMapper.selectUserInfoList(userInfo);
    }

    // 微信授权登录-远程调用
    @Override
    public UserInfo wxLogin(String code) {
        // 1. 拿着code + 微信公众号平台id + 秘钥 请求微信接口服务，返回openId
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String openId = sessionInfo.getOpenid();
            // 2. 拿着openId 查询数据库表，如果表中没有openId值，表示第一次登录，添加用户信息
            UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getWxOpenId, openId));
            if (null == userInfo) {
                // 用户第一次登录，添加用户信息
                userInfo = new UserInfo();
                userInfo.setNickname(String.valueOf(System.currentTimeMillis()));
                userInfo.setAvatarUrl("https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
                userInfo.setWxOpenId(openId);
                userInfoMapper.insert(userInfo);
            }

            // 3. 返回userInfo 用户信息
            return userInfo;
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean updateUserLogin(UpdateUserLogin updateUserLogin) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(updateUserLogin.getUserId());
        userInfo.setLastLoginIp(updateUserLogin.getLastLoginIp());
        userInfo.setLastLoginTime(updateUserLogin.getLastLoginTime());
        userInfoMapper.updateById(userInfo);

        //登录日志
//        UserLoginLog userLoginLog = new UserLoginLog();
//        userLoginLog.setUserId(userInfo.getId());
//        userLoginLog.setMsg("小程序登录");
//        userLoginLog.setIpaddr(updateUserLogin.getLastLoginIp());
//        userLoginLogMapper.insert(userLoginLog);
        return true;
    }

}
