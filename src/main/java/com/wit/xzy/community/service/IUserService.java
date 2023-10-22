package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.User;

import java.util.Map;

/**
 * @Author ZongYou
 **/
public interface IUserService extends IService<User> {
    User selectById(int userId);
    Map<String, Object> CheckAndRegister(User user);
    int activation(int userId,String code);
}
