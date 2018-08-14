package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.SimpleImageAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
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
    TextView tv_name;
    MyRatingBar my_rating_bar;
    ServiceTagView stv_tag;

    LinearLayout ll_select_service,ll_comment;
    Button btn_call, btn_submit;

    PopService popService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_detail;
    }

    @Override
    public void initView() {
        convenientBanner = $(R.id.convenientBanner);
        iv_head = $(R.id.iv_head);
        tv_name = $(R.id.tv_name);
        my_rating_bar = $(R.id.my_rating_bar);
        stv_tag = $(R.id.stv_tag);
        ll_select_service = $(R.id.ll_select_service);
        ll_comment = $(R.id.ll_comment);
        btn_call = $(R.id.btn_call);
        btn_submit = $(R.id.btn_submit);

        initCommont();
    }

    public void initCommont(){
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

        SimpleImageAdapter enterAdapter = new SimpleImageAdapter(context,banners);
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
        String service = "优秀";
        services.add(service);
        services.add(service);
        stv_tag.setServiceTag(services);

        ll_select_service.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            
        }
    }

    private void showPopService() {
        showShortToast("展示服务内容");

        if (popService == null){
            popService = new PopService(context, btn_submit);
        }
        popService.showPopupWindow(btn_submit);
    }
}
