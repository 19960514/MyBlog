package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.Message;
import com.wit.xzy.community.mapper.MessageMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Author ZongYou
 **/
public interface IMessageService extends IService<Message> {

    public List<Message> findConversations(int userId, int offset, int limit);

    public int findConversationCount(int userId);

    public List<Message> findLetters(String conversationId, int offset, int limit);

    public int findLetterCount(String conversationId);

    public int findLetterUnreadCount(int userId, String conversationId);

    public int addMessage(Message message);

    public int readMessage(List<Integer> ids);

}
