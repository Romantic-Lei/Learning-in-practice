package com.luojia.service;

import com.luojia.anotation.Bean;
import com.luojia.anotation.Di;
import com.luojia.dao.UserDao;

@Bean
public class UserServiceImpl implements UserService{

    @Di
    private UserDao userDao;

    @Override
    public void out() {
        userDao.print();
        System.out.println("Service层执行结束");
    }
}
