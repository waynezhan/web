package com.webserve.web.service;

import com.webserve.web.bean.user;

public interface UserService {
    String login(String username,String password);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return
     */
    user findUserByName(String name);
}
