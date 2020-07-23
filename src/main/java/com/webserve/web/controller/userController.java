package com.webserve.web.controller;

import com.webserve.web.bean.user;
import com.webserve.web.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class userController {

    @Autowired
    userMapper userMapper;

    @GetMapping("/user/{id}")
    public user getUser(@PathVariable("id") Integer id){
        return userMapper.getUserById(id);
    }
}
