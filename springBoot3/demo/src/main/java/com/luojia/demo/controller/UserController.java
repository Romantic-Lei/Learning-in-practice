package com.luojia.demo.controller;

import com.luojia.demo.entity.UserEntity;
import com.luojia.demo.event.EvnetPublisher;
import com.luojia.demo.event.LoginSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private EvnetPublisher evnetPublisher;

    @GetMapping("/login")
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("passwd") String passwd) {

        LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent(new UserEntity(username, passwd));
        evnetPublisher.sendEvent(loginSuccessEvent);

        return username + "完成了登录";
    }
}
