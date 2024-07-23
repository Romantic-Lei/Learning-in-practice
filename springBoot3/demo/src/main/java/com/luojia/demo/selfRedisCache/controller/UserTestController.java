package com.luojia.demo.selfRedisCache.controller;

import com.luojia.demo.entity.User;
import com.luojia.demo.selfRedisCache.annotation.LuoJiaRedisCache;
import com.luojia.demo.selfRedisCache.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserTestController {
    @Resource
    private UserService userService;

    @PostMapping(value = "/user/add")
    public int addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable("id") Integer id)
    {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/aop/user/add")
    public int addUserAop(@RequestBody User user)
    {
        return userService.addUserAop(user);
    }

    @GetMapping(value = "/aop/user/{id}")
    public User getUserByIdAop(@PathVariable("id") Integer id)
    {
        return userService.getUserByIdAop(id);
    }


}
