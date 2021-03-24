package com.hxd.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hxd.blog.common.lang.Result;
import com.hxd.blog.entity.Blog;
import com.hxd.blog.service.BlogService;
import com.hxd.blog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer size){
        Page blogList = blogService.findListByPage(currentPage, size);
        PageInfo pageInfo = new PageInfo(blogList);
        return Result.succ(pageInfo);
    }


    @GetMapping("/{id}")
    public Result listOne(@PathVariable(name = "id") Long id){

        Blog blog = blogService.findOne(id);

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog tmp = null;
        //编辑
        if (blog.getId() != null){
            tmp = blogService.findOne(blog.getId());
            Assert.isTrue(tmp.getUserId().equals(ShiroUtils.getProfile().getId()) ,"没有权限编辑");


            BeanUtil.copyProperties(tmp,blog,"title","description","content");
            blogService.update(blog);
            return Result.succ(null);
        }
        //添加
        else {
            tmp = new Blog();
            tmp.setUserId(ShiroUtils.getProfile().getId());
            tmp.setCreated(LocalDateTime.now());
            tmp.setStatus(0);
            BeanUtil.copyProperties(blog,tmp,"id","userId","created","status");
            blogService.add(tmp);
            return Result.succ(null);
        }
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    public Result delete(@Validated @RequestBody Blog blog){
        Blog tmp = null;
        if (blog.getId() != null){
            tmp = blogService.findOne(blog.getId());
            Assert.isTrue(tmp.getUserId().equals(ShiroUtils.getProfile().getId()) ,"没有权限编辑");
            BeanUtil.copyProperties(tmp,blog,"title","description","content");

            blogService.delete(blog);
            return Result.succ(null);
        }else{
            return Result.fail("删除失败");
        }




    }




}
