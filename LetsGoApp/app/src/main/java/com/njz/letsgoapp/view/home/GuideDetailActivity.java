package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.content.Intent;
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
import com.njz.letsgoapp.constant.Constant;
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

        webView.getSettings().setJavaScriptEnabled(true);//支持js
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadDataWithBaseURL(null, Constant.HTML_TEST, "text/html" , "utf-8", null);
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
                startActivity(new Intent(context, CommentDetailActivity.class));
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
