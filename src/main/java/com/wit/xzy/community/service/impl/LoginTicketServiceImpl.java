package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.entity.LoginTicket;

import com.wit.xzy.community.mapper.LoginTicketMapper;

import com.wit.xzy.community.service.ILoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author ZongYou
 **/
@Service
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketMapper, LoginTicket> implements ILoginTicketService {
    @Resource
    private LoginTicketMapper loginTicketMapper;
    //退出登录将登录凭证的状态更改为失效即1
    @Override
    public void updateTicketStatus(String ticket) {
        UpdateWrapper<LoginTicket> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ticket",ticket);
        updateWrapper.set("status",1);
        loginTicketMapper.update(null,updateWrapper);
    }

    @Override
    public void insertTicket(LoginTicket loginTicket) {
        loginTicketMapper.insert(loginTicket);
    }

    @Override
    public LoginTicket selectByTicket(String ticket) {
        QueryWrapper<LoginTicket>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ticket",ticket);
        LoginTicket loginTicket = loginTicketMapper.selectOne(queryWrapper);
        return loginTicket;
    }
}
