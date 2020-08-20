package com.webserve.web.config;


import com.webserve.web.shiro.AccountRealm;
import com.webserve.web.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.server.session.DefaultWebSessionManager;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
//    @Autowired
//    JwtFilter jwtFilter;

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(AccountRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }



    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean =new ShiroFilterFactoryBean();


        //添加shiro的内置过滤器

        /*
            anon: 无需认证就可访问
            authc：必须认证才能访问
            user：必须拥有记住我功能才能访问
            perms: 拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
       */

        //拦截
        Map<String, Filter> filterMap = new LinkedHashMap<>();

        filterMap.put("jwt", new JwtFilter());
        bean.setFilters(filterMap);
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>();
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");

        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/getUser", "anon");
        filterRuleMap.put("/createUser","anon");

        filterRuleMap.put("/user/imgCode", "anon");
        //开放API文档接口
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/webjars/**","anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/v2/**","anon");
        //sql监控
        filterRuleMap.put("/druid/**","anon");

        bean.setFilterChainDefinitionMap(filterRuleMap);
        return bean;
    }
    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
