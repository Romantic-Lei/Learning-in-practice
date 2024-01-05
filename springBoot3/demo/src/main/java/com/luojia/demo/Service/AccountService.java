package com.luojia.demo.Service;

import com.luojia.demo.entity.UserEntity;
import com.luojia.demo.event.LoginSuccessEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service
public class AccountService implements ApplicationListener<LoginSuccessEvent> {
    public void addAccountScore(String username) {
        System.out.println(username + "加了1分");
    }

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        System.out.println("========= AccountService 收到事件 =========" + event);
        UserEntity userEntity = (UserEntity) event.getSource();
        addAccountScore(userEntity.getUsername());
    }
}
