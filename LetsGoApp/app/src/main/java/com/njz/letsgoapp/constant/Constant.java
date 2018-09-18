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

    public static final String HTML_TEST = "<article class=\"o-single-content__body__content c-article-content s-single-article js-article\">\n" +
            "  <p>一年一度的黑客朝圣大会 DEF CON 又在美国拉斯维加斯举行了，各种五花八门的网络安全隐患被翻出来讨论，手机后端、物联网和语音攻击在今年被频繁提及。</p>\n" +
            "<p><img class=\"aligncenter size-full wp-image-1084267\" src=\"https://images.ifanr.cn/wp-content/uploads/2018/08/defcon.jpg\" alt=\"\" width=\"807\" height=\"454\" srcset=\"https://images.ifanr.cn/wp-content/uploads/2018/08/defcon.jpg 807w, https://images.ifanr.cn/wp-content/uploads/2018/08/defcon-360x203.jpg 360w, https://images.ifanr.cn/wp-content/uploads/2018/08/defcon-768x432.jpg 768w\" sizes=\"(max-width: 807px) 100vw, 807px\"></p>\n" +
            "<p>不过，<a href=\"https://qz.com/1354713/an-11-year-old-hacked-a-mock-florida-election-site-at-defcon/\">儿童组一项黑掉美国中期选举网站的比赛</a>，却让美国人开始担心下半年中期选举结果的真实性了。</p>\n" +
            "<p>在这项名为「投票村」（DefCon Voting Machine Hacking Village）的比赛中，主办方向参赛的 39 个儿童提供了 6 个州的选举网站复制品，让他们去修改选票数量、政党名称、候选人名称等。</p>\n" +
            "<p><img class=\"aligncenter size-full wp-image-1084268\" src=\"https://images.ifanr.cn/wp-content/uploads/2018/08/p0wnyb0y-Audrey-1st-2nd-break-in-1200x775.jpg\" alt=\"\" width=\"1200\" height=\"775\" srcset=\"https://images.ifanr.cn/wp-content/uploads/2018/08/p0wnyb0y-Audrey-1st-2nd-break-in-1200x775.jpg 1200w, https://images.ifanr.cn/wp-content/uploads/2018/08/p0wnyb0y-Audrey-1st-2nd-break-in-1200x775-360x233.jpg 360w, https://images.ifanr.cn/wp-content/uploads/2018/08/p0wnyb0y-Audrey-1st-2nd-break-in-1200x775-768x496.jpg 768w, https://images.ifanr.cn/wp-content/uploads/2018/08/p0wnyb0y-Audrey-1st-2nd-break-in-1200x775-1024x661.jpg 1024w\" sizes=\"(max-width: 1200px) 100vw, 1200px\"></p>\n" +
            "<div class=\"editor-image-source\">\n" +
            "<p>▲ 参赛儿童，图片来自：pbs.org</p>\n" +
            "</div>\n" +
            "<p>其中 35 名参赛儿童完成了任务，一位名为 Emmett Brewer 的 11 岁参赛选手，在 10 分钟内就改写了佛罗里达州选举网站的选举结果。你 11 岁的时候还在做什么？</p>\n" +
            "<p>培养黑客（白帽子）要从娃娃抓起早已不新鲜，不过这样的比赛和比赛结果，倒是让人们开始怀疑：那些用来投票的网站和设备，是不是遍布漏洞？选举结果还可以相信吗？</p>\n" +
            "<p>这时候，负责组织各个州选举的全美州务卿协会（National Association of Secretaries of State，NASS）出来表示：</p>\n" +
            "<blockquote><p>我们关注到 DEF CON 大会上用来攻击的环境是模拟出来的，并且这些软件和硬件都已经是过时的了。</p></blockquote>\n" +
            "<p>同时美国最大的选举设备供应商之一 ES&amp;S 也立即回应，他们所提供的选举设备在实际使用过程时，并不会像大会的场景那样，会给黑客有物理入侵的机会。</p>\n" +
            "<p>面对选举设备供应商这些想要和稀泥的言论，DEF CON 也发表了声明，认为 ES&amp;S 的回应并没有消除公众的怀疑，他们要做的应该是在公众知道选举设备的脆弱后，重建民众的信心。</p>\n" +
            "<p>题图来自 DEF CON</p>\n" +
            "\n" +
            "  </article>";


    //banner图默认时间 轮播
    public static final int BANNER_RUNNING_TIME = 4000;

    //分页默认参数
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE = 1;

    //banner type 1，首页banner 2，导游详情banner(存放导游照片) 3，向导陪游banner 4，私人订制banner 5，车导服务 6，代订酒店 7，代订景点门票
    public static final int BANNER_HOME = 1;
    public static final int BANNER_GUIDE = 2;
    public static final int BANNER_SERVICE_GUIDE = 3;
    public static final int BANNER_SERVICE_CUSTOM = 4;
    public static final int BANNER_SERVICE_CAR = 5;
    public static final int BANNER_SERVICE_HOTEL = 6;
    public static final int BANNER_SERVICE_TICKET = 7;

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
