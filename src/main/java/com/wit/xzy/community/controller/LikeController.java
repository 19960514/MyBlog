package com.wit.xzy.community.controller;

import com.wit.xzy.community.entity.User;
import com.wit.xzy.community.service.IUserService;
import com.wit.xzy.community.service.impl.LikeService;
import com.wit.xzy.community.util.Commonuitl;
import com.wit.xzy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZongYou
 **/
@Controller
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId){

        User user = hostHolder.getUser();
        //点赞
        likeService.LikeEntity(user.getId(),entityType,entityId);

        //点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType,entityId);

        //点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);
        return Commonuitl.getJSONString(0, null, map);
    }
}
