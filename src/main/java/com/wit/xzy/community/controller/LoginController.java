package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author ZongYou
 **/
@Controller
public class LoginController {



    @GetMapping("/register")
    public String getRegister(){
        return "/site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }
    @GetMapping("/login")
    public String login(Model model){
        //登录页面输入有1.账号名(username)，2.密码(password),3.验证码，4.记住我
        //与注册相同，1,2,3都需要验证输入是否准备，先验证验证码
        //如何实现动态验证码
        return "/site/login";
    }
}
