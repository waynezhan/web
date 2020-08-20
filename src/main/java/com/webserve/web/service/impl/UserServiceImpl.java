package com.webserve.web.service.impl;

import com.webserve.web.bean.user;
import com.webserve.web.common.exception.ErrorCodeEnum;
import com.webserve.web.common.exception.ServiceException;
import com.webserve.web.mapper.userMapper;
import com.webserve.web.service.UserService;
import com.webserve.web.shiro.JwtToken;
import com.webserve.web.util.JwtUtils;
import com.webserve.web.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private userMapper userMapper;

    @Override
    public String login(String username, String password) {
        String token;
        user user = findUserByName(username);
        if (user != null) {
            String salt = user.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JwtUtils.sign(username, target);
            JwtToken jwtToken = new JwtToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            throw new ServiceException(ErrorCodeEnum.USER_ACCOUNT_NOT_FOUND);
        }
        System.out.println(username+"登陆成功");
        return token;
    }


    public user findUserByName(String name) {
        return userMapper.getUserByUsername(name);
    }
}
