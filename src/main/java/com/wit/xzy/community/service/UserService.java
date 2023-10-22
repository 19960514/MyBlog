package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.User;

import java.util.Map;

/**
 * @Author ZongYou
 **/
public interface UserService extends IService<User> {


    Map<String,Object> CheckAndRegister(User user);
}
