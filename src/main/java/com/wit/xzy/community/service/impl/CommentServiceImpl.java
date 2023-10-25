package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.controller.LoginController;
import com.wit.xzy.community.entity.Comment;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.mapper.CommentMapper;
import com.wit.xzy.community.service.ICommentService;
import com.wit.xzy.community.service.IDiscussPostService;
import com.wit.xzy.community.util.HostHolder;
import com.wit.xzy.community.util.SensitiveFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

import static com.wit.xzy.community.util.SystemConstants.ENTITY_TYPE_POST;

/**
 * @Author ZongYou
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private IDiscussPostService discussPostService;

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

    //新增评论
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insert(comment);
        // 更新帖子评论数量
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            int count =findCommentCount(comment.getEntityType(), comment.getEntityId());
            UpdateWrapper<DiscussPost>updateWrapper  = new UpdateWrapper<>();
            updateWrapper.eq("id",comment.getEntityId())
                          .set("comment_count",count);
            discussPostService.update(null,updateWrapper);
        }
return rows;
    }
}
