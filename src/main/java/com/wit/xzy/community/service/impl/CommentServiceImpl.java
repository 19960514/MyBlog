package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.entity.Comment;
import com.wit.xzy.community.mapper.CommentMapper;
import com.wit.xzy.community.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ZongYou
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int Limit) {
        //通过entityType与entityId唯一确定一个实体，并查询该实体关联的所有评论
        Page<Comment>page = new Page<>(offset,Limit);
        QueryWrapper<Comment>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0)
                    .eq("entity_type",entityType)
                    .eq("entity_id",entityId)
                    .orderByAsc("create_time");
        Page<Comment> commentPage = commentMapper.selectPage(page, queryWrapper);
        return commentPage.getRecords();
    }


    @Override
    public int findCommentCount(int entityType, int entityId) {
        QueryWrapper<Comment>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0)
                    .eq("entity_type",entityType)
                    .eq("entity_id",entityId);
        return commentMapper.selectCount(queryWrapper);
    }
}
