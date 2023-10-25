package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.Comment;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.Page;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.service.ICommentService;
import com.wit.xzy.community.service.IDiscussPostService;
import com.wit.xzy.community.service.IUserService;
import com.wit.xzy.community.util.Commonuitl;
import com.wit.xzy.community.util.HostHolder;
import com.wit.xzy.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

import static com.wit.xzy.community.util.SystemConstants.ENTITY_TYPE_COMMENT;
import static com.wit.xzy.community.util.SystemConstants.ENTITY_TYPE_POST;

/**
 * @Author ZongYou
 **/
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private IDiscussPostService discussPostService;


    @Autowired
    private IUserService userService;


    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ICommentService commentService;


    @PostMapping("/add")
    @ResponseBody
    public String publishDisscussPost(String title, String content){
        User user = hostHolder.getUser();
        if (user == null) {
            return Commonuitl.getJSONString(403, "你还没有登录哦!");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());

        discussPostService.addDisscusspost(post);
        return  Commonuitl.getJSONString(0, "发布成功!");
    }



    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page) {
        //查询帖子详情
        DiscussPost post = discussPostService.getDiscussdetail(discussPostId);
        model.addAttribute("post",post);
        User user = userService.selectById(post.getUserId());
        model.addAttribute("user",user);


        //显示帖子详情，帖子本身可以被评论(comment)，评论也可以被回复(reply)。两个对象
        //TODO
        //1.所有评论分页显示
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());


        List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, post.getId(), page.getOffset() + 1, 5);
        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment", comment);
                // 作者
                commentVo.put("user", userService.selectById(comment.getUserId()));

                // 回复列表
                List<Comment> replyList = commentService.findCommentsByEntity(
                        ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        // 回复
                        replyVo.put("reply", reply);
                        // 作者
                        replyVo.put("user", userService.selectById(reply.getUserId()));
                        // 回复目标
                        User target = reply.getTargetId() == 0 ? null : userService.selectById(reply.getTargetId());
                        replyVo.put("target", target);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys", replyVoList);

                // 回复数量
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);
        return "/site/discuss-detail";
    }
}
