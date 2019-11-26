package com.yf.cloud.secret;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.yf.cloud.ibusiness.IUser;
import com.yf.cloud.info.UserTable;

public class MyShiroRealm  extends SimpleAccountRealm{
	 /**
     * 注入 userService 用于查询用户数据
     */
    @Autowired
    private IUser iUser;
    /*@Autowired
    private MemorySessionDAO sessionDAO;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	 Map<String, Object> param = new HashMap<String, Object>();
    	//加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        System.out.println( "username="+username+",password="+password );
 
     // 获取用户名。通过 username 找到该用户
        param.put("username", username);
        UserTable user = iUser.getUserTableLoginInfo(param);
        if( !"1".equals(user.getStatus()) ){
            throw new LockedAccountException();
        }
 
        if( username.equals( user.getUsername() ) && password.equals( user.getPassword() ) ){
        	 
            // 获取所有session
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session: sessions) {
            	UserTable sysUser = (UserTable)session.getAttribute("USER_SESSION");
                // 如果session里面有当前登陆的，则证明是重复登陆的，则将其剔除
                if( sysUser!=null ){
                    if( username.equals( sysUser.getUsername() ) ){
                        session.setTimeout(0);
                        throw new LockedAccountException();
                    }
                }
            }
        }
 
        // 从数据库查询出来的用户名密码，进行验证
        // 用户名，密码，密码盐值，realm 名称
        // 登陆的时候直接调用 subject.login() 即可自动调用该方法
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                authenticationToken.getPrincipal() , user.getPassword() , getName()
        );
 
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("USER_SESSION", user);
        return info;
    }*/

   @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	 Map<String, Object> param = new HashMap<String, Object>();
        String username = token.getPrincipal().toString();
        String password = iUser.getPasswordByUsername(username);
     // 获取用户名。通过 username 找到该用户
        param.put("username", username);
        if (password != null) {//这里的密码是数据库中的密码 //返回Realm名
           AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password, getName());  
        
          return authenticationInfo;
        }
        return null;
    }

}
