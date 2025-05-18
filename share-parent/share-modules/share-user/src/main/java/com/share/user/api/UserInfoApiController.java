package com.share.user.api;

import com.share.common.core.domain.R;
import com.share.common.core.web.controller.BaseController;
import com.share.common.security.annotation.InnerAuth;
import com.share.user.api.domain.UpdateUserLogin;
import com.share.user.domain.UserInfo;
import com.share.user.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
public class UserInfoApiController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;


    @Operation(summary = "小程序授权登录")
    @InnerAuth
    @GetMapping("/userInfo/wxLogin/{code}")
    public R<UserInfo> wxLogin(@PathVariable String code) {
        return R.ok(userInfoService.wxLogin(code));
    }

    @Operation(summary = "更新用户登录信息")
    @InnerAuth
    @PutMapping("/updateUserLogin")
    public R<Boolean> updateUserLogin(@RequestBody UpdateUserLogin updateUserLogin) {
        return R.ok(userInfoService.updateUserLogin(updateUserLogin));
    }

}