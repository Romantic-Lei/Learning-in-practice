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


/*

POST http://localhost:24618/user/add
Accept: application/json
Content-Type: application/json
{
"username": "尚硅谷04",
"password": "13911111112",
"sex": "1"
}

*/