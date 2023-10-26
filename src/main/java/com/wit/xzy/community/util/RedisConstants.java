package com.wit.xzy.community.util;

/**
 * @Author ZongYou
 **/
public class RedisConstants {

    //对帖子和评论点赞
    //like:entity
    //entityType点赞的具体类型(帖子or评论)
    //entityId  点赞的具体对象
    //Key:like:entity:entityType:entityId -> Value ：set(userId)
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_FLOWER = "follow";

    public static String getEntityKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }


    //如果想设计对个用户的点赞类推
    private static final String PREFIX_USER_LIKE = "like:user";
    public static String getUserKey(int userId){
        return PREFIX_USER_LIKE + SPLIT + userId;
    }



    String getFollowKey(int entityType,int userId){
        return PREFIX_USER_FLOWER + SPLIT + userId + SPLIT + entityType;
    }



}
