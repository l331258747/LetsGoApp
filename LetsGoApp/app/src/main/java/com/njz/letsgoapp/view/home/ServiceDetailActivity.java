package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.map.MapActivity;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.util.webview.MyWebViewClient;
import com.njz.letsgoapp.widget.PriceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceDetailActivity extends BaseActivity implements View.OnClickListener {

    ConvenientBanner convenientBanner;
    TextView tv_title, tv_destination, tv_sell,tv_submit;
    PriceView pv_price;
    LWebView webView;

    String title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra("ServiceDetailActivity_title");
        if(TextUtils.isEmpty(title)){
            title = "";
        }
    }

    @Override
    public void initView() {
        showLeftAndTitle(title + "详情介绍");
        showRightIv();
        getRightIv().setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(),R.mipmap.icon_share));
        getRightIv().setOnClickListener(this);

        convenientBanner = $(R.id.convenientBanner);
        tv_title = $(R.id.tv_title);
        tv_destination = $(R.id.tv_destination);
        tv_sell = $(R.id.tv_sell);
        pv_price = $(R.id.pv_price);
        tv_submit = $(R.id.tv_submit);
        webView = $(R.id.webview);

        tv_submit.setOnClickListener(this);
        tv_destination.setOnClickListener(this);

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


        tv_title.setText("我是导游，选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我选我");
        StringUtils.setHtml(tv_destination,String.format(getResources().getString(R.string.destination), "张家界"));
        tv_sell.setText("已售" + 11);
        pv_price.setPrice(300d);


        webView.getSettings().setJavaScriptEnabled(true);//支持js
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadDataWithBaseURL(null, Constant.HTML_TEST, "text/html" , "utf-8", null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                showShortToast("预订成功");
                break;
            case R.id.tv_destination:
                startActivity(new Intent(context, MapActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // webview 需要加载空界面来释放资源
        webView.loadUrl("about:blank");
        webView.clearCache(false);
        webView.destroy();
    }
}
