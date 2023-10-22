package com.wit.xzy.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.xzy.community.entity.DiscussPost;
import com.wit.xzy.community.mapper.DiscussPostMapper;
import com.wit.xzy.community.service.IDiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ZongYou
 **/
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements IDiscussPostService {


    @Resource
    private DiscussPostMapper discussPostMapper;
    /*
    select <include refid="selectFields"></include>
        from discuss_post
        where status != 2
        <if test="userId!=0">
            and user_id = #{userId}
        </if>
        order by type desc, create_time desc
        limit #{offset}, #{limit}
     */
    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        Page<DiscussPost>page = new Page<>(offset,limit);
        QueryWrapper<DiscussPost>queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("user_id",0);
        queryWrapper.ne("status",2);
        queryWrapper.orderByDesc("type","create_time");
        Page<DiscussPost> postPage = discussPostMapper.selectPage(page, queryWrapper);
        List<DiscussPost> records = postPage.getRecords();

        return records;
    }


    /**
     * 查询全部status！=2的全部帖子
     * @return
     */
    @Override
    public int selectCount() {
        QueryWrapper<DiscussPost>queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status",2);
        queryWrapper.orderByDesc("type","create_time");
        Integer count = discussPostMapper.selectCount(queryWrapper);
        return count;
    }
}
