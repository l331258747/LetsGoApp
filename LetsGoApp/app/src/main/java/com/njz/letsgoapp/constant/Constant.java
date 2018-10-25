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

    public static final String DEFAULT_CITY = "张家界";

//    public static final String SERVICE_TYPE_CUSTOM = "私人定制";
//    public static final String SERVICE_TYPE_HOTEL = "代订酒店";
//    public static final String SERVICE_TYPE_TICKET = "代订门票";
//    public static final String SERVICE_TYPE_CAR = "车导服务";
//    public static final String SERVICE_TYPE_GUIDE = "向导陪游";

    public static final String SERVICE_TYPE_SHORT_CUSTOM = "srdz";
    public static final String SERVICE_TYPE_SHORT_HOTEL = "ddjd";
    public static final String SERVICE_TYPE_SHORT_TICKET = "ddjdmp";
    public static final String SERVICE_TYPE_SHORT_CAR = "cdfw";
    public static final String SERVICE_TYPE_SHORT_GUIDE = "xdpy";


    public static final int ORDER_PAY_WAIT = 0;//待付款
    public static final int ORDER_PAY_ALREADY = 1;//已支付
    public static final int ORDER_PAY_FINISH = 2;//已完成
    public static final int ORDER_PAY_REFUND = 3;//退款单

    public static final int ORDER_WAIT_PAY = 0;//待付款
    public static final int ORDER_WAIT_PAYING = 1;//付款中

    public static final int ORDER_TRAVEL_WAIT = 0;//导游待确认
    public static final int ORDER_TRAVEL_NO_GO = 1;//未出行
    public static final int ORDER_TRAVEL_GOING = 2;//行程中
    public static final int ORDER_TRAVEL_FINISH = 3;//行程结束
    public static final int ORDER_TRAVEL_REFUSE = 4;//导游拒绝接单

    public static final int ORDER_EVALUATE_NO = 0;//未点评
    public static final int ORDER_EVALUATE_YES = 1;//已点评

    public static final int ORDER_REFUND_WAIT = 0;//导游待审核
    public static final int ORDER_REFUND_PROCESS = 1;//退款中
    public static final int ORDER_REFUND_FINISH = 2;//已退款


    //------消息 start-------

    public static final String NOTIFY_TYPE_SYSTEM_MSG = "xtxx";//系统消息
    public static final String NOTIFY_TYPE_INTERACTION = "lyhd";//驴友互动
    public static final String NOTIFY_TYPE_DISCOUNT = "yhhd";//优惠活动Discount
    public static final String NOTIFY_TYPE_COMMON_MSG = "xxtx";//消息提醒
    public static final String NOTIFY_TYPE_ORDER_MSG = "ddxx";//订单消息
    public static final String NOTIFY_TYPE_VERIFY_MSG = "shxx";//审核消息
    public static final String NOTIFY_TYPE_REFUND_MSG = "ttxx";//退款消息
    public static final String NOTIFY_TYPE_REPORT_MSG = "jbxx";//举报消息
    public static final String NOTIFY_TYPE_OPINION_MSG = "yjxx";//意见消息Opinion


    //------消息 end-------



}
