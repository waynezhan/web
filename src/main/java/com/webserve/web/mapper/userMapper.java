package com.webserve.web.mapper;

import com.webserve.web.bean.user;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

//这是一个操作数据库的mapper

@Component
@Mapper
public interface userMapper {
//    @Select("select * from user where userid=#{id}")
    public user getUserById(Integer id);

//    @Select("insert into user(username) values(*#{username})")
//    public int insertUser(user user);

}
