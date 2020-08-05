package com.webserve.web.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.webserve.web.bean.user;
import com.webserve.web.common.dto.LoginDto;
import com.webserve.web.common.lang.Result;
import com.webserve.web.util.JwtUtils;
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

@RestController
public class AccountController {

    @Autowired
    private userController usercontroller;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response, Model model) {



        String username = loginDto.getUsername();

        String password = loginDto.getPassword();

        user user = usercontroller.getUserByUsername(username);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try{
            subject.login(token);
            return null;
        }catch (UnknownAccountException e)
        {
            model.addAttribute("msg","用户名错误");
        }catch (Exception e){
            System.out.println(e);
        }





        return  null;


//        Assert.notNull(user, "用户不为空");
//
//        if (user.getPassword() != password) {
//            return Result.fail("密码不正确");
//        }
//
//        String jwt = jwtUtils.generateToken(user.getUserid());
//
//        response.setHeader("Authorization", jwt);
//
//        response.setHeader("Access-control-Expose-Header", "Authorization");
//
//        return Result.success(MapUtil.builder()
//                .put("id", user.getUserid())
//                .put("username", user.getUsername())
//                .map());


    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
