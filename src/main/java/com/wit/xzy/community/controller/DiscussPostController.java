package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.User;
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

import java.util.Date;

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



    @GetMapping("/detail/{discusspostId}")
    public String getDiscussPostDetail(@PathVariable("discusspostId") int discusspostId, Model model){
        //查询帖子详情
        DiscussPost discussdetail = discussPostService.getDiscussdetail(discusspostId);
        model.addAttribute("post",discussdetail);
        User user = userService.selectById(discussdetail.getUserId());
        model.addAttribute("user",user);
        return "/site/discuss-detail";
    }
}
