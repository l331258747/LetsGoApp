package com.njz.letsgoapp.constant;

/**
 * Created by LGQ
 * Time: 2018/7/19
 * Function: 常量
 */

public class Constant {
    //微信
    public static final String WEIXIN_APP_ID = "wxef1b5eeb18b9ea8b";


    //文件路径
    public static final String BASE_PATH = "letsgoapp";
    public static final String LOG_PATH = "log";
    public static final String IMAGE_PATH = "image";

    //banner图默认时间 轮播
    public static final int BANNER_RUNNING_TIME = 4000;

    //分页默认参数
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE = 1;

    //banner type 1，首页banner 2，导游详情banner(存放导游照片) 3，向导陪游banner 4，私人订制banner 5，车导服务 6，代订酒店 7，代订景点门票
    public static final int BANNER_HOME = 1;
    public static final int BANNER_GUIDE = 2;

    //导游列表 type1 综合 2 销量 3得分 4评论次数
    public static final int GUIDE_TYPE_SYNTHESIZE = 1;
    public static final int GUIDE_TYPE_COUNT = 2;
    public static final int GUIDE_TYPE_SCORE = 3;
    public static final int GUIDE_TYPE_COMMENT = 4;

    public static final String DEFAULT_CITY = "长沙";

    public static final String SERVICE_TYPE_CUSTOM = "私人定制";
    public static final String SERVICE_TYPE_HOTEL = "代订酒店";
    public static final String SERVICE_TYPE_TICKET = "代订门票";
    public static final String SERVICE_TYPE_CAR = "车导服务";
    public static final String SERVICE_TYPE_GUIDE = "向导陪游";

}
