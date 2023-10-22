package com.wit.xzy.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.xzy.community.entity.DiscussPost;

import java.util.List;

/**
 * @Author ZongYou
 **/
public interface IDiscussPostService extends IService<DiscussPost> {
    List<DiscussPost> findDiscussPosts(int userId, int offset, int limit);
    int selectCount();
}
