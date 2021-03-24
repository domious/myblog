package com.hxd.blog.controller;

import cn.hutool.crypto.SecureUtil;
import com.hxd.blog.common.dto.LoginDto;
import com.hxd.blog.common.lang.Result;
import com.hxd.blog.entity.User;
import com.hxd.blog.service.UserService;
import com.hxd.blog.shiro.JwtFilter;
import com.hxd.blog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.RuntimeMBeanException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/Login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user1 = new User();
        user1.setUsername(loginDto.getUsername());
        user1.setPassword(loginDto.getPassword());
        User user = userService.login(user1);

        if (user == null){
            Assert.notNull(user, "用户不存在");
            //throw new RuntimeException("用户不存在");
        }
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
            //throw new RuntimeException("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        Map map = new HashMap();
        map.put("id",user.getId());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());
        map.put("email", user.getEmail());

        return Result.succ(map);
    }

    @RequiresAuthentication
    @GetMapping ("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }


}
