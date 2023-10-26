package com.wit.xzy.community.service.impl;

import com.wit.xzy.community.util.Commonuitl;
import com.wit.xzy.community.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Author ZongYou
 **/
@Service
public class LikeService {



    @Autowired
    private RedisTemplate redisTemplate;
    /**
     *
     * @param userId
     * @param entityType 被点赞实体的类型
     * @param entityId 被点赞实体的ID
     */
    public void LikeEntity(int userId,int entityType,int entityId){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //获取key
                String LikeKey = RedisConstants.getEntityKey(entityType, entityId);
                String LikeUser = RedisConstants.getUserKey(userId);
                //先判断Redis中是否有当前对该实体的点赞(查询key:vaule是否存在)
                Boolean ismember = operations.opsForSet().isMember(LikeKey, userId);

                operations.multi();//开启事务

                if(ismember){
                    operations.opsForSet().remove(LikeKey,userId);
                    operations.opsForValue().decrement(LikeUser);
                }else {
                   operations.opsForSet().add(LikeKey,userId);
                   operations.opsForValue().increment(LikeUser);
                }
                return operations.exec();
            }
        });

        }

    // 查询某个用户获得的赞
    public long findEntityLikeCount(int entityType, int entityId) {
        String LikeKey = RedisConstants.getEntityKey(entityType, entityId);
        return redisTemplate.opsForSet().size(LikeKey);
    }

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String LikeKey = RedisConstants.getEntityKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(LikeKey, userId) ? 1 : 0;
    }
    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) {
        String LikeUser = RedisConstants.getUserKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(LikeUser);
        return count == null ? 0 : count.intValue();
    }

}
