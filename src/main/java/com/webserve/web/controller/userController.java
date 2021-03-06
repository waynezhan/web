package com.webserve.web.controller;

import com.webserve.web.bean.user;
import com.webserve.web.mapper.userMapper;
import com.webserve.web.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class userController {

    @Autowired
    userMapper userMapper;

    @GetMapping("/user/{id}")
    public user getUser(@PathVariable("id") Integer id){
        return userMapper.getUserById(id);
    }
    @GetMapping("/user/{username}")
    public user getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }


    @GetMapping("/getUser")
    public List<user> findAll(){
        return userMapper.getAllUser();
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody user user){
        String password = user.getPassword();
        String salt = user.getSalt();
        password = MD5Utils.md5Encryption(password,salt);
        user.setPassword(password);


        userMapper.createUser(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());


    }

}
