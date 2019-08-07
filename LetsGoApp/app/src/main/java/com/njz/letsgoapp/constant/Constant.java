package com.njz.letsgoapp.constant;

/**
 * Created by LGQ
 * Time: 2018/7/19
 * Function: 常量
 */

public class Constant {
    //微信
    public static final String WEIXIN_APP_ID = "wxef1b5eeb18b9ea8b";
    public static final String WEIXIN_APP_KRY = "d69aa8110651bb0732665e0698efea98";

    //QQ
    public static final String QQ_APP_ID = "101541305";
    public static final String QQ_APP_KEY = "206df1ab8faa4eb49aa22535ed185430";


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
    public static final int GUIDE_TYPE_PRICE = 4;

    public static final String DEFAULT_CITY = "全部";

//    public static final String SERVICE_TYPE_CUSTOM = "私人定制";
//    public static final String SERVICE_TYPE_HOTEL = "代订酒店";
//    public static final String SERVICE_TYPE_TICKET = "代订门票";
//    public static final String SERVICE_TYPE_CAR = "车导服务";
//    public static final String SERVICE_TYPE_GUIDE = "向导陪游";
//    public static final String SERVICE_TYPE_SHORT_CUSTOM = "srdz";
//    public static final String SERVICE_TYPE_SHORT_HOTEL = "ddjd";
//    public static final String SERVICE_TYPE_SHORT_TICKET = "ddjdmp";
//    public static final String SERVICE_TYPE_SHORT_CAR = "cdfw";
//    public static final String SERVICE_TYPE_SHORT_GUIDE = "xdpy";

    //xdpy向导陪游 tsty特色体验 srdz私人定制 ddjd代订酒店 ddmp代订门票 jsjz接送机/站
    public static final String SERVER_TYPE_GUIDE = "xdpy";
    public static final String SERVER_TYPE_FEATURE = "tsty";
    public static final String SERVER_TYPE_CUSTOM = "srdz";
    public static final String SERVER_TYPE_HOTEL = "ddjd";
    public static final String SERVER_TYPE_TICKET = "ddmp";
    public static final String SERVER_TYPE_CAR = "jsjz";
    public static final int SERVER_TYPE_GUIDE_ID = 1;
    public static final int SERVER_TYPE_FEATURE_ID = 4;
    public static final int SERVER_TYPE_CUSTOM_ID = 3;
    public static final int SERVER_TYPE_HOTEL_ID = 5;
    public static final int SERVER_TYPE_TICKET_ID = 6;
    public static final int SERVER_TYPE_CAR_ID = 2;


    public static final int ORDER_PAY_WAIT = 0;//待付款
    public static final int ORDER_PAY_ALREADY = 1;//已支付
    public static final int ORDER_PAY_FINISH = 2;//已完成
//    public static final int ORDER_PAY_CANCEL = 3;//已取消
    public static final int ORDER_PAY_REFUND = 4;//退款单

    public static final int ORDER_WAIT_PAY = 0;//待付款
    public static final int ORDER_WAIT_PAYING = 1;//付款中-

    public static final int ORDER_PLAN_GUIDE_WAIT = 0;//导游待确认
    public static final int ORDER_PLAN_PLANING = 1;//方案设计中
    public static final int ORDER_PLAN_USER_WAIT = 2;//游客待确认
//    public static final int ORDER_PLAN_USER_COMFIRM = 3;//游客已确认
//    public static final int ORDER_PLAN_GUIDE_REFUND = 4;//导游已拒绝

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
    public static final int ORDER_REFUND_CANCEL = 3;//已取消
    public static final int ORDER_REFUND_PLAN_REFUSE = 4;//私人定制，导游拒绝

    //--------配置 start-------------
    public static final String CONFIG_XB = "xb";//导游性别
    public static final String CONFIG_DYNL = "dynl";//导游年龄
    public static final String CONFIG_CYNX = "cynx";//服务年限
    public static final String CONFIG_FWLX = "fwlx";//服务类型
    public static final String CONFIG_YYLX = "yylx";//语言类型
    public static final String CONFIG_TKYY = "tkyy";//退款原因
    public static final String CONFIG_QXYY = "qxyy";//取消原因

    //--------配置 end-------------


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

    //跳转信息
    public static final String NOTIFY_SKIP_FSD = "FSD";//动态详情
    public static final String NOTIFY_SKIP_OD = "OD";//订单详情
    public static final String NOTIFY_SKIP_ORD = "ORD";//退款单详情
    public static final String NOTIFY_SKIP_UD = "UD";//用户详情
    public static final String NOTIFY_SKIP_GD = "GD";//导游详情
    public static final String NOTIFY_SKIP_CL = "CL";//优惠卷详情

    //------消息 end-------

    public static final String TEST_IMG_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543913738509&di=e287d2a10c4a2d80d4f6c14d16bfbaa2&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D701022721%2C856869386%26fm%3D214%26gp%3D0.jpg";


    /**
     * url 跳转链接
     **/
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String IS_USE_WIDE_VIEW_PORT = "isUseWideViewPort";


    //im 密码
    public static final String IM_PASSWORD = "najiuzou@im";
    //欢迎语
    public static final String IM_WELCOME = "welcom_msg";


}
