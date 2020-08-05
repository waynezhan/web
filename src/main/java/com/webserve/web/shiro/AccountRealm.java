package com.webserve.web.shiro;

import com.webserve.web.bean.user;
import com.webserve.web.controller.userController;
import com.webserve.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    private userController userControl;

    @Autowired
    JwtUtils jwtUtils;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入doGetAuthorizationInfo");

        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        log.info("进入doGetAuthenticationInfo");
        System.out.println("进入验证方法");

        String name = "zhang";
        String password = "23312";
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        if (!userToken.getUsername().equals(name)){
            return null;
        }

        return  new SimpleAuthenticationInfo("",password,"");

//
//        Subject subject = SecurityUtils.getSubject();
//
//
//
//        if (user==null){
//            throw new UnknownAccountException("账户不存在");
//        }
//        //用户状态
//        if(user.getUserid()==0) {
//            throw new LockedAccountException("用户被锁定");
//        }
//        AccountProfile profile = new AccountProfile();
//        BeanUtils.copyProperties(user,profile);
//
//
//        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());

    }
}
