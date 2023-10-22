package com.wit.xzy.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.Page;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.mapper.DiscussPostMapper;
import com.wit.xzy.community.mapper.UserMapper;
import com.wit.xzy.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZongYou
 **/
@Controller
@RequestMapping("/myblog")
public class HomeController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;


    //@RequestMapping(path = "/index", method = RequestMethod.GET)
    @GetMapping("/index")
    public String getHomePage(Model model, Page page ){
        //查询帖子状态不等于2的所有帖子
        QueryWrapper<DiscussPost> Wrapper = new QueryWrapper<>();
        Wrapper.ne("status",2);
        Integer count = discussPostMapper.selectCount(Wrapper);

        page.setRows(count);
        page.setPath("/index");

        List<DiscussPost> list = discussPostMapper.findDiscussPosts(0, page.getOffset(), page.getLimit());
        //System.out.println(list.size());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list!=null){
           for(DiscussPost post:list){
               Map<String, Object> map = new HashMap<>();
               map.put("post",post);
               User user = userMapper.selectById(post.getUserId());
               map.put("user",user);
               discussPosts.add(map);
           }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

}
