package com.hxd.blog.util;

import com.hxd.blog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
