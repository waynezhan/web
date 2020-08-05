package com.webserve.web;

import com.webserve.web.bean.user;
import com.webserve.web.controller.userController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    private userController usercontroller;


    @Test
    public void contextLoads() throws SQLException {
          System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
    @Test
    public void sqlTest(){


        user user = usercontroller.getUserByUsername("但丁");
        System.out.println(user.getUsername());
    }

}
