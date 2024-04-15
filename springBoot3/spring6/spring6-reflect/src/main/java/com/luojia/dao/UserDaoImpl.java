package com.luojia.dao;

import com.luojia.anotation.Bean;

@Bean
public class UserDaoImpl implements UserDao{
    @Override
    public void print() {
        System.out.println("Dao层执行结束");
    }
}
