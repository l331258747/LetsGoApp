package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.SimpleImageAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.widget.GuideAuthenticationView;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList;
import com.njz.letsgoapp.widget.popupwindow.PopService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class GuideDetailActivity extends BaseActivity implements View.OnClickListener {

    ConvenientBanner convenientBanner;
    ImageView iv_head;
    TextView tv_name, tv_service_num,tv_content;
    MyRatingBar my_rating_bar;
    ServiceTagView stv_tag;

    LinearLayout ll_select_service, ll_comment;
    Button btn_call, btn_submit;

    PopService popService;
    GuideLabelView guideLabel;
    GuideAuthenticationView guide_authentication;

    LWebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_detail;
    }

    @Override
    public void initView() {

        showLeftAndTitle("向导详情介绍");
        showRightIv();
        getRightIv().setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.icon_share));
        getRightIv().setOnClickListener(this);

        convenientBanner = $(R.id.convenientBanner);
        iv_head = $(R.id.iv_head);
        tv_name = $(R.id.tv_name);
        my_rating_bar = $(R.id.my_rating_bar);
        stv_tag = $(R.id.stv_tag);
        ll_select_service = $(R.id.ll_select_service);
        ll_comment = $(R.id.ll_comment);
        btn_call = $(R.id.btn_call);
        btn_submit = $(R.id.btn_submit);
        guideLabel = $(R.id.guide_label);
        tv_service_num = $(R.id.tv_service_num);
        guide_authentication = $(R.id.guide_authentication);
        tv_content = $(R.id.tv_content);
        webView = $(R.id.webview);

        initCommont();
    }

    public void initCommont() {
        ImageView commont_head = $(R.id.commont_head);

        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(context, photo, commont_head);


        RecyclerView mRecyclerView = $(R.id.recycler_view);
        mRecyclerView.setNestedScrollingEnabled(false);//滑动取消
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                mRecyclerView.getContext(), 4));

        List<String> banners = new ArrayList<>();
        String bannerImg = "http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg";
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);

        SimpleImageAdapter enterAdapter = new SimpleImageAdapter(context, banners);
        mRecyclerView.setAdapter(enterAdapter);


    }

    @Override
    public void initData() {

        List<String> banners = new ArrayList<>();
        String bannerImg = "http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg";
        banners.add(bannerImg);
        banners.add(bannerImg);
        banners.add(bannerImg);


        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView(new LocalImageHolderView.BannerListener<String>() {

                    @Override
                    public void bannerListener(Context context, int position, String data, ImageView view) {
                        GlideUtil.LoadImage(context, data, view);
                    }
                });
            }
        }, banners)
                .setPointViewVisible(true) //设置指示器是否可见
                .startTurning(4000)//设置自动切换（同时设置了切换时间间隔）
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
//                    .setOnItemClickListener(this) //设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）


        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(context, photo, iv_head);

        my_rating_bar.setRating(4);
        List<String> services = new ArrayList<>();
        services.add("4年");
        services.add("英语");
        services.add("中文");
        services.add("泰语");
        services.add("葡萄牙语");
        stv_tag.setServiceTag(services);
        List<String> tabels = new ArrayList<>();
        tabels.add("幽默达人");
        tabels.add("风趣性感");
        tabels.add("旅游玩家高手");
        guideLabel.setTabel(tabels);
        tv_service_num.setText("服务" + 6000 + "次");
        List<Integer> authentications = new ArrayList<>();
        authentications.add(GuideAuthenticationView.AUTHENT_IDENTITY);
        authentications.add(GuideAuthenticationView.AUTHENT_CAR);
        guide_authentication.setAuthentication(authentications);
        tv_content.setText("选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我");

        ll_select_service.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_submit.setOnClickListener(this);


        String str = "<article class=\"o-single-content__body__content c-article-content s-single-article js-article\">\n" +
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

        webView.getSettings().setJavaScriptEnabled(true);//支持js
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadDataWithBaseURL(null, str, "text/html" , "utf-8", null);
    }

    //图片过大处理
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // webview 需要加载空界面来释放资源
        webView.loadUrl("about:blank");
        webView.clearCache(false);
        webView.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_service:
                showPopService();
                break;
            case R.id.ll_comment:
                //TODO 评论列表

                break;
            case R.id.btn_call:
                showShortToast("联系客服");
                break;
            case R.id.btn_submit:
                showPopService();
                break;
            case R.id.right_iv:
                ShareDialog dialog = new ShareDialog(activity,
                        "那就走 标题",
                        "那就走 内容",
                        "http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg",
                        "https://www.ifanr.com/1084256");
                dialog.show();
                break;

        }
    }

    private void showPopService() {
        showShortToast("展示服务内容");

        if (popService == null) {
            popService = new PopService(activity, btn_submit);
        }
        popService.showPopupWindow(btn_submit);
    }
}
