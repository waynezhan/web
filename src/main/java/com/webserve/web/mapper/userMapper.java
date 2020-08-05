package com.webserve.web.mapper;

import com.webserve.web.bean.user;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

//这是一个操作数据库的mapper

@Component
@Mapper
public interface userMapper {
//    @Select("select * from user where userid=#{id}")
    public user getUserById(Integer id);

    public user getUserByUsername(String username);

    public List<user> getAllUser();

    public void createUser(user user);

//    @Select("insert into user(username) values(*#{username})")
//    public int insertUser(user user);

}
