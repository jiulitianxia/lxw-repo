package com.yf.cloud.secret;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.springframework.beans.factory.annotation.Autowired;

import com.yf.cloud.ibusiness.IUser;

public class MyShiroRealm  extends SimpleAccountRealm{
	 /**
     * 注入 userService 用于查询用户数据
     */
    @Autowired
    private IUser iUser;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String password = iUser.getPasswordByUsername(username);

        if (password != null) {//这里的密码是数据库中的密码 //返回Realm名
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password, getName());

            return authenticationInfo;
        }
        return null;
    }

}
