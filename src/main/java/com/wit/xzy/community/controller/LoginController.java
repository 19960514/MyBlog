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


//    @RequestMapping(path = "/register", method = RequestMethod.GET)
//    public String getRegisterPage() {
//        return "/site/register";
//    }
//
//    @RequestMapping(path = "/login", method = RequestMethod.GET)
//    public String getLoginPage() {
//        return "/site/login";
//    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String getRegister(){
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }
}
