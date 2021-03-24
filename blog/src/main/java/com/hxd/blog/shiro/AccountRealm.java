package com.hxd.blog.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.hxd.blog.entity.User;
import com.hxd.blog.service.UserService;

import com.hxd.blog.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;


    /**
     * 用于认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {



        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    /**
     * 用于授权
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AccountProfile accountProfile = new AccountProfile();
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = new User();
        user.setId(Long.valueOf(userId));
        User user1 = userService.findById(user);

        if (user1 == null){
            throw new RuntimeException("账户不存在");
        }
        if (user1.getStatus() == -1){
            throw new RuntimeException("账户锁定");
        }
        BeanUtil.copyProperties(user1,accountProfile);


        return new SimpleAuthenticationInfo(accountProfile,jwtToken.getCredentials(),getName());
    }
}
