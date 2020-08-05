package com.webserve.web.config;


import com.webserve.web.shiro.AccountRealm;
import com.webserve.web.shiro.JwtFilter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.server.session.DefaultWebSessionManager;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
    @Autowired
    JwtFilter jwtFilter;



    @Bean
    public ShiroFilterFactoryBean getShiroFactoryBean(@Qualifier("sercurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean =new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/getUser","authc");
        filterMap.put("/login", "anon");

        bean.setFilterChainDefinitionMap(filterMap);




        return bean;
    }


    @Bean(name="sercurityManager")
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        return securityManager;
    }


}
