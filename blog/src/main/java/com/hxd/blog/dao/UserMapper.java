package com.hxd.blog.dao;

import com.hxd.blog.entity.User;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-03-11
 */
public interface UserMapper extends Mapper<User> {

    @Select("select * from m_user where username=#{username}")
    public  User selectUsernameAndPassword (@Param("username") String username);

}
