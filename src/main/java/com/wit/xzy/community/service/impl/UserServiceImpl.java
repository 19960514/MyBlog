package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.mapper.UserMapper;
import com.wit.xzy.community.service.IUserService;
import com.wit.xzy.community.util.Commonuitl;
import com.wit.xzy.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.wit.xzy.community.util.SystemConstants.*;

/**
 * @Author ZongYou
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MailClient mailClient;

    @Resource
    private TemplateEngine templateEngine;

    @Override
    public Map<String,Object> CheckAndRegister(User user){
        Map<String,Object> map = new HashMap<>();
        //1.验证表单输入是否合法
        //1.1 不合法，返回错误信息
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }
        //1.2 合法
        //1.2.1 验证数据库是否存在该用户
        //1.2.1.1 存在   返回相关信息
        //验证用户名是否存在
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User u = userMapper.selectOne(queryWrapper);

        //User u = userService.getOne(queryWrapper);
        if (u != null) {
            map.put("usernameMsg", "该账号已存在!");
            return map;
        }
        // 验证邮箱是否已使用
        QueryWrapper<User>queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("email",user.getEmail());
        User u2 = userMapper.selectOne(queryWrapper2);
        //User u2 = userService.getOne(queryWrapper2);
        //u2 = userMapper.selectByEmail(user.getEmail());
        if (u2 != null) {
            map.put("emailMsg", "该邮箱已被注册!");
            return map;
        }

        //1.2.1.2 不存在 存入数据库
        //先构建完整User对象，前端传入的只有Username，email，password。并对密码加密。
        // 注册用户
        user.setSalt(Commonuitl.generateUUID().substring(0, 5));
        user.setPassword(Commonuitl.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(Commonuitl.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insert(user);
        //根据业务需求，注册成功后，发送一个带激活链接的邮件到注册的邮箱，点击后更改user表中的Status属性0->1
        //发送邮件需要使用MailClient
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String url = DOMAIN + contextPath + "/activation/" +user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);
        return  map;
    }

    @Override
    public int activation(int userId,String code) {
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        User user = userMapper.selectOne(queryWrapper);//根据Id查询用户

        UpdateWrapper<User>updateWrapper = new UpdateWrapper<>();
        if(user.getStatus()==1){
            return ACTIVATION_REPEAT ;//重复激活状态码
        }else if(user.getActivationCode().equals(code)){
            updateWrapper.set("status",1);
            userMapper.update(user,updateWrapper);
            //userService.update(updateWrapper);
            return ACTIVATION_SUCCESS ;//激活成功状态码
        }else {
            return ACTIVATION_FAILURE ;//激活失败状态码
        }
    }

    @Override
    public User selectById(int userId) {
        return userMapper.selectById(userId);
    }
}
