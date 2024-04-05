package com.luojia.netty.nettypro.netty.groupchat.demo2.server.service;

public class UserServiceFactory {

    private static UserServiceMemoryImpl userService = new UserServiceMemoryImpl();

    public static UserService getUserService() {
        return userService;
    }
}
