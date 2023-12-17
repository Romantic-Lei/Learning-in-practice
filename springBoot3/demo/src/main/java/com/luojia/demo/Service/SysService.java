package com.luojia.demo.Service;

import com.luojia.demo.entity.UserEntity;
import com.luojia.demo.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class SysService {

    @Order(3)
    @EventListener
    public void onEvent(LoginSuccessEvent event) {
        System.out.println("===== SysService 感知到事件 =====" + event);
        UserEntity source = (UserEntity) event.getSource();
        recodeLog(source.getUsername());
    }

    public void recodeLog(String username) {
        System.out.println(username + " 登录信息已被记录");
    }
}
