package com.hxd.blog.service.impl;


import com.hxd.blog.dao.UserMapper;
import com.hxd.blog.entity.User;
import com.hxd.blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User login(User user) {
        User user1 = userMapper.selectUsernameAndPassword(user.getUsername());


        return user1;
    }

    @Override
    public User findById(User user){
        User user1 = userMapper.selectByPrimaryKey(user);
        return user1;
    }

}
