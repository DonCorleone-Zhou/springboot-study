package com.kuang.config;

import com.kuang.pojo.User;
import com.kuang.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
//Authentication“验证” Authorization 授权、委托
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行了Authorization授权");
        //给用户授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        //设置当前用户权限
        info.addStringPermission(currentUser.getPerms());


        return info;
    }

//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了Authentication认证");



        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());
        if (user==null){
            return null;
        }

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("loginUser",user);

        return new SimpleAuthenticationInfo(user,user.getPwd(),"");
    }
}
