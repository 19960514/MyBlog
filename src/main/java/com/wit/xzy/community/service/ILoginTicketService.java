package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.LoginTicket;
import com.wit.xzy.community.mapper.LoginTicketMapper;

/**
 * @Author ZongYou
 **/
public interface ILoginTicketService extends IService<LoginTicket> {

    void insertTicket(LoginTicket loginTicket);
    void updateTicketStatus(String ticket);//修改登录凭证的状态
    LoginTicket selectByTicket(String ticket);
}
