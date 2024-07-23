package com.luojia.demo.selfRedisCache.service;

import com.luojia.demo.entity.User;

public interface UserService {
    public int addUser(User user);

    public User getUserById(Integer id);

    public int addUserAop(User user);

    public User getUserByIdAop(Integer id);
}
