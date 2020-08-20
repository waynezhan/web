package com.webserve.web.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.webserve.web.bean.user;
import com.webserve.web.common.dto.LoginDto;
import com.webserve.web.common.lang.Result;
import com.webserve.web.service.UserService;
import com.webserve.web.shiro.ResponseBean;
import com.webserve.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
public class AccountController {

    @Autowired
    private UserService userService;


    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseBean login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response, Model model) {


        String username = loginDto.getUsername();

        String password = loginDto.getPassword();

        String token = userService.login(username, password);

        log.info(token);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return ResponseBean.success((Object)token);


    }


    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
