package com.hxd.blog.service;

import com.hxd.blog.entity.User;



public interface UserService  {

    User login(User user);

    User findById(User user);
}
