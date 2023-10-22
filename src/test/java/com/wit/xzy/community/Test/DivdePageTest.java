package com.wit.xzy.community.Test;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.Page;
import com.wit.xzy.community.mapper.DiscussPostMapper;
import com.wit.xzy.community.service.IDiscussPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author ZongYou
 **/
@SpringBootTest
public class DivdePageTest {
    @Autowired
    private IDiscussPostService discussPostService;

//    @Test
//    void TestPageD(){
//        //mybatis-plus实现
//        //TODO
//        //1.新建分页对象Page设置分页new Page<>(当前页数currentPage,每页显示条数count);
//        Page<DiscussPost>page = new Page<>(2,10);
//        Page<DiscussPost> postPage = discussPost.selectPage(page, null);
//        List<DiscussPost> records = postPage.getRecords();
//
//        for(DiscussPost s:records){
//            System.out.println(s.toString());
//        }
//    }


    @Test
    void TestfindDby(){
        Page page = new Page();
        int count = discussPostService.selectCount();
        page.setRows(count);
        page.setPath("/index");
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, 10, page.getLimit());
        System.out.println("分页取数据： "+discussPosts.size());
        System.out.println("记录总条数： "+count);
        for(DiscussPost s:discussPosts){
            System.out.println(s);
        }
    }
}
