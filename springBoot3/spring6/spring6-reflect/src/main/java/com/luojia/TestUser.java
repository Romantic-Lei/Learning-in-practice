package com.luojia;

import com.luojia.bean.AnnotationApplicationContext;
import com.luojia.bean.MyApplicationContext;
import com.luojia.service.UserService;

public class TestUser {

    public static void main(String[] args) {
        MyApplicationContext context =
                new AnnotationApplicationContext("com.luojia");
        UserService userService = (UserService)context.getBean(UserService.class);
        userService.out();
    }

}
