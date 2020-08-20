package com.webserve.web.shiro;

import com.webserve.web.bean.user;
import com.webserve.web.controller.userController;
import com.webserve.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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

    @Autowired
    userController userController;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addStringPermission("user:getUser");
        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        log.info("进入doGetAuthenticationInfo");
        System.out.println("进入验证方法");


        String token = (String) authenticationToken.getCredentials();

        user user = userController.getUserByUsername(JwtUtils.getUsername(token));

        String username =JwtUtils.getUsername(token);

        System.out.println("username token"+JwtUtils.getUsername(token));


        if (user==null){
            throw new AccountException("账号不存在!");
        }if(JwtUtils.isExpire(token)){
            throw new AuthenticationException(" token过期，请重新登入！");
        }

        if (! JwtUtils.verify(token, username, user.getPassword())) {
            throw new CredentialsException("密码错误!");
        }

//        if(userBean.getStatus()==0){
//            throw new LockedAccountException("账号已被锁定!");
//        }

        return  new SimpleAuthenticationInfo(user,token,getName());

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
