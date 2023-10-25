package com.wit.xzy.community.util;

/**
 * @Author ZongYou
 **/
public class SystemConstants {

    public static final String DOMAIN = "http://localhost:8080";
    public static final String contextPath = "/myblog";
    public static final int ACTIVATION_SUCCESS = 0;//激活成功状态码
    public static final int ACTIVATION_REPEAT = 1;//重复激活状态码
    public static final int ACTIVATION_FAILURE = 2;//激活失败状态码
    public static final String uploadPath = "d:/work/data/upload";//头像文件存放路径


    /**
     * 默认状态的登录凭证的超时时间
     */
    public static final int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态的登录凭证超时时间
     */
    public static final int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;



    /**
     * 实体类型: 帖子
     */
    public static final int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型: 评论
     */
    public static final int ENTITY_TYPE_COMMENT = 2;
}
