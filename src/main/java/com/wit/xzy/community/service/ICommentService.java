package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.Comment;
import com.wit.xzy.community.mapper.CommentMapper;

import java.util.List;

/**
 * @Author ZongYou
 **/
public interface ICommentService extends IService<Comment> {


    List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int Limit);


    int findCommentCount(int entityType, int entityId);


    int addComment(Comment comment);
}
