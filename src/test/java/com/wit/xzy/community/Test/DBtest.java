package com.wit.xzy.community.Test;

import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author ZongYou
 **/

@SpringBootTest
public class DBtest {


    @Resource
    private UserMapper userMapper;


    @Test
    void getuserByid(){
        User user = userMapper.selectById(1);
        System.out.println(user);


    }
}
