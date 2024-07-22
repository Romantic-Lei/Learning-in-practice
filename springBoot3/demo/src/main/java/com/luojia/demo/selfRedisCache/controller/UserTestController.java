package com.luojia.demo.selfRedisCache.controller;

import com.luojia.demo.entity.User;
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
}
