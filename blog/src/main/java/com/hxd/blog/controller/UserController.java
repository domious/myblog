package com.hxd.blog.controller;


import com.hxd.blog.common.lang.Result;
import com.hxd.blog.entity.User;
import com.hxd.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/index")
    public Result index(){
        User user = new User();
        user.setId(1L);
        User user1 = userService.findById(user);
        return Result.succ(user1);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){


        return Result.succ(user);
    }

}
