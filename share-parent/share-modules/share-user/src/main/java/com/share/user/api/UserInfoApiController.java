package com.share.user.api;

import com.share.common.core.context.SecurityContextHolder;
import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.core.web.domain.AjaxResult;
import com.share.common.security.annotation.InnerAuth;
import com.share.common.security.annotation.RequiresLogin;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.domain.UserInfo;
import com.share.user.domain.UserVo;
import com.share.user.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoApiController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;

    @Operation(summary = "小程序授权登录")
    @GetMapping("/wxLogin/{code}")
    public R<UserInfo> wxLogin(@PathVariable String code) {
        return R.ok(userInfoService.wxLogin(code));
    }

    @Operation(summary = "获取当前登录用户信息")
    @RequiresLogin
    @GetMapping("/getLoginUserInfo")
    public AjaxResult getLoginUserInfo(HttpServletRequest request) {
        Long userId = SecurityContextHolder.getUserId();
        UserInfo userInfo = userInfoService.getById(userId);
        UserVo userInfoVo = new UserVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return success(userInfoVo);
    }

    @Operation(summary = "更新用户登录信息")
    @InnerAuth
    @PutMapping("/updateUserLogin")
    public R<Boolean> updateUserLogin(@RequestBody UpdateUserLogin updateUserLogin) {
        return R.ok(userInfoService.updateUserLogin(updateUserLogin));
    }

    @Operation(summary = "是否免押金")
    @RequiresLogin
    @GetMapping("/isFreeDeposit")
    public AjaxResult isFreeDeposit() {
        return success(userInfoService.isFreeDeposit());
    }

    @Operation(summary = "获取用户详细信息")
    @GetMapping(value = "/getUserInfo/{id}")
    public R<UserInfo> getInfo(@PathVariable("id") Long id) {
        UserInfo userInfo = userInfoService.getById(id);
        return R.ok(userInfo);
    }

    //统计2024年每个月注册人数
    //远程调用：统计用户注册数据
    @GetMapping("/getUserCount")
    public R getUserCount() {
        //[150, 230, 224, 218, 135, 147, 260]
        //['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        Map<String,Object> map = userInfoService.getUserCount();
        return R.ok(map);
    }

}