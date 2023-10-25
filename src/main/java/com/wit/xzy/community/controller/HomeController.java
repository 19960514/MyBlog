package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.Page;
import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.service.IDiscussPostService;
import com.wit.xzy.community.service.IUserService;
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
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDiscussPostService discussPostService;


    @GetMapping("/index")
    public String getHomePage(Model model, Page page){
        int count = discussPostService.selectCount();
        page.setRows(count);
        page.setPath("/index");
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset()+1, page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list!=null){
           for(DiscussPost post:list){
               Map<String, Object> map = new HashMap<>();
               map.put("post",post);
               User user = userService.selectById(post.getUserId());
               map.put("user",user);
               discussPosts.add(map);
           }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }

}
