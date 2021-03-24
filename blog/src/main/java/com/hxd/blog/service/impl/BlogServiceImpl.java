package com.hxd.blog.service.impl;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxd.blog.dao.BlogMapper;
import com.hxd.blog.entity.Blog;
import com.hxd.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BlogServiceImpl  implements BlogService {

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public Page findListByPage(Integer page, Integer size) {
        Page BlogPage = PageHelper.startPage(page, size);
        blogMapper.selectAll();
        return BlogPage;
    }

    @Override
    public Blog findOne(Long id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        return blog;
    }

    @Override
    @Transactional
    public void update(Blog blog) {
        blogMapper.updateByPrimaryKey(blog);
    }

    @Override
    @Transactional
    public void add(Blog blog) {
        blogMapper.insertSelective(blog);
    }

    @Override
    @Transactional
    public void delete(Blog blog) {
        blogMapper.deleteByPrimaryKey(blog);
    }
}
