package at.luojia.spring6.iocxml.auto.controller;

import at.luojia.spring6.iocxml.auto.server.UserService;

public class UserController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void addUser() {
        System.out.println("UserController-addUser 方法执行了...");
        // 调用 service 方法
        userService.addUserService();
    }
}
