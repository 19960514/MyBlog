package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.LoginTicket;
import com.wit.xzy.community.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author ZongYou
 **/
public interface IUserService extends IService<User> {
    User selectById(int userId);
    Map<String, Object> CheckAndRegister(User user);
    int activation(int userId,String code);
    //验证登录输入，登录成功后生成登录凭证
    Map<String, Object> verifyAccount(String username, String password, int expiredSeconds);
    void updateStatus(int id);
    User selectByName(String username);//通过用户姓名查询用户
    LoginTicket findLoginTicket(String ticket);

    int updateHeader(int userId, String headerUrl);//用户上传自己的头像

}
