package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.mapper.UserMapper;
import com.wit.xzy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ZongYou
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}
