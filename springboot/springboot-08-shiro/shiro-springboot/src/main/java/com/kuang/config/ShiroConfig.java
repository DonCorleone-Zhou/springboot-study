package com.kuang.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //shiroFilterFactoryBean  step3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器filter
        /*
            anon： 无需认证就可以访问
            authc： 必须认证了才能访问
            user： 必须拥有记住我功能才能用
            perms： 拥有对某个资源的权限才能访问
            role： 拥有某个角色权限
         */
        HashMap<String,String> filterMap = new LinkedHashMap<>();
        //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);

        bean.setLoginUrl("/toLogin");

        //设置未授权页面
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    //DefaultWebSecurityManager step2
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }


    //创建realm对象 需要自定义类  step1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合shiroDialect 整合shiro和thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
