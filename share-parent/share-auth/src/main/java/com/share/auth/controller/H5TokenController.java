package com.share.auth.controller;

import com.share.auth.service.H5LoginService;
import com.share.auth.service.SysLoginService;
import com.share.common.core.domain.R;
import com.share.common.security.service.TokenService;
import com.share.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * token 控制
 * 
 * @author share
 */
@RestController
public class H5TokenController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private H5LoginService h5LoginService;

    @Autowired
    private SysLoginService sysLoginService;

    @GetMapping("/h5/login/{code}")
    public R<?> login(@PathVariable String code) {
        // 用户登录
        LoginUser userInfo = h5LoginService.login(code);
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }


}
