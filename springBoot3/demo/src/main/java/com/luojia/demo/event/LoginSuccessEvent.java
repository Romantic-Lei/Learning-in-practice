package com.luojia.demo.event;

import com.luojia.demo.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

public class LoginSuccessEvent extends ApplicationEvent {
    public LoginSuccessEvent(UserEntity source) {
        super(source);
    }

}
