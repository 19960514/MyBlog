package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.mapper.UserMapper;
import com.wit.xzy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ZongYou
 **/
@Controller
public class RegisterController {

//    @Autowired
//    private UserService userService;

    @PostMapping("/register")
    public String register(Model model, User user){
        //TODO
        //1.验证表单输入是否合法
        //1.1 不合法，返回错误信息
        //1.2 合法
        //1.2.1 验证数据库是否存在该用户
        //1.2.1.1 不存在 存入数据库
        //1.2.1.2 存在   返回相关信息
        //交由业务层Service完成
        return "/site/register";
    }


}
