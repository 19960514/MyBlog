package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.Comment;
import com.wit.xzy.community.service.ICommentService;
import com.wit.xzy.community.service.IDiscussPostService;
import com.wit.xzy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @Author ZongYou
 **/
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private HostHolder hostHolder;


    @PostMapping("/add/{discussPostId}")
    public String addcomment(@PathVariable("discussPostId")int discussPostId, Comment comment){

        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);
        return "redirect:/discuss/detail/" + discussPostId;

    }
}
