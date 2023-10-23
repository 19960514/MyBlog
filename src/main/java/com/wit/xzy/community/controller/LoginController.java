package com.wit.xzy.community.controller;

import com.google.code.kaptcha.Producer;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.wit.xzy.community.util.SystemConstants.DEFAULT_EXPIRED_SECONDS;
import static com.wit.xzy.community.util.SystemConstants.REMEMBER_EXPIRED_SECONDS;
import static com.wit.xzy.community.util.SystemConstants.contextPath;

/**
 * @Author ZongYou
 **/
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String getRegister(){
        return "/site/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/site/login";
    }




    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码图片存入Session
        session.setAttribute("kaptcha",text);

        //将验证码图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, String code, boolean rememberme,
                        Model model, HttpSession session, HttpServletResponse response){
        //登录页面输入有1.账号名(username)，2.密码(password),3.验证码，4.记住我
        //与注册相同，1,2,3都需要验证输入是否准备，先验证验证码
        //1.如何实现动态验证码
        //2.verifyAccount()方法负责验证1,2,3输入是否正确，1,2是否与数据库对应

        //TODO
        //1.getKaptcha将生成验证码存入Session中，此处要从session中取出来，与输入的code对比
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }
        //2.验证码处理完毕，处理输入的username和password,登录凭证的时效即
        //rememberme 勾选记住我，登录凭证保留15天，
        int expiredSeconds = rememberme?REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;
        Map<String,Object> map = userService.verifyAccount(username,password,expiredSeconds);

        if (map.containsKey("ticket")) {
            //登录成功，将登录凭证放入浏览器的cookie中
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }


//退出登录，要将登录凭证的状态吗变更为失效状态
    //ticket存放在cookie中
    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){
        if(ticket!=null){
            userService.updateTicketStatus(ticket);
        }
        return "redirect:/login";
    }
}
