package com.wit.xzy.community.Test;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.entity.Page;
import com.wit.xzy.community.mapper.DiscussPostMapper;
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
    private DiscussPostMapper discussPost;

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
        QueryWrapper<DiscussPost> Wrapper = new QueryWrapper<>();
        Wrapper.ne("status",2);
        Integer count = discussPost.selectCount(Wrapper);

        page.setRows(count);
        page.setPath("/");
        List<DiscussPost> discussPosts = discussPost.findDiscussPosts(0, page.getOffset(), page.getLimit());
        System.out.println(discussPosts.size());
        System.out.println(count);

    }
}
