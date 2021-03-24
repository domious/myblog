package com.hxd.blog.service;

import com.github.pagehelper.Page;
import com.hxd.blog.entity.Blog;

import java.util.List;

public interface BlogService  {

    Page findListByPage(Integer page,Integer size);

    Blog findOne(Long id);

    void update(Blog blog);

    void add(Blog blog);

    void delete(Blog blog);

}
