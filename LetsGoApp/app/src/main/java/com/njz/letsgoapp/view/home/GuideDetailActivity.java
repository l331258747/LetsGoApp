package com.njz.letsgoapp.view.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.SimpleImageAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.CommentModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DefaultDialog;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.mvp.home.GuideDetailContract;
import com.njz.letsgoapp.mvp.home.GuideDetailPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.widget.GuideAuthenticationView;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;
import com.njz.letsgoapp.widget.popupwindow.PopService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function:
 */

public class GuideDetailActivity extends BaseActivity implements View.OnClickListener, GuideDetailContract.View {

    ConvenientBanner convenientBanner;
    ImageView iv_head;
    TextView tv_name, tv_service_num, tv_comment_content, tv_content, tv_back_top, btn_submit;
    MyRatingBar my_rating_bar;
    ServiceTagView stv_tag;
    NestedScrollView scrollView;

    LinearLayout ll_select_service, btn_call;

    PopService popService;
    GuideLabelView guideLabel;
    GuideAuthenticationView guide_authentication;

    LWebView webView;

    GuideDetailPresenter mPresenter;
    DefaultDialog defaultDialog;

    int guideId;
    public static final String GUIDEID = "GUIDEID";

    @Override
    public void getIntentData() {
        super.getIntentData();
        guideId = intent.getIntExtra(GUIDEID, -1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_detail;
    }

    @Override
    public void initView() {

        if (guideId == -1) {
            showShortToast("导游详情获取失败");
            finish();
        }

        showLeftAndTitle("向导详情介绍");
        showRightIv();
        getRightIv().setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(), R.mipmap.icon_share));
        getRightIv().setOnClickListener(this);

        convenientBanner = $(R.id.convenientBanner);
        iv_head = $(R.id.iv_head);
        tv_name = $(R.id.tv_name);
        my_rating_bar = $(R.id.my_rating_bar);
        stv_tag = $(R.id.stv_tag);
        ll_select_service = $(R.id.ll_select_service);
        tv_content = $(R.id.tv_content);
        btn_call = $(R.id.btn_call);
        btn_submit = $(R.id.btn_submit);
        guideLabel = $(R.id.guide_label);
        tv_service_num = $(R.id.tv_service_num);
        guide_authentication = $(R.id.guide_authentication);
        tv_comment_content = $(R.id.tv_comment_content);
        tv_back_top = $(R.id.tv_back_top);
        webView = $(R.id.webview);
        scrollView = $(R.id.scrollView);

        tv_back_top.setVisibility(View.GONE);
        tv_back_top.setOnClickListener(this);

        ll_select_service.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    //    public void initCommont() {
//        ImageView commont_head = $(R.id.commont_head);
//
//        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
//        GlideUtil.LoadCircleImage(context, photo, commont_head);
//
//
//        RecyclerView mRecyclerView = $(R.id.recycler_view);
//        mRecyclerView.setNestedScrollingEnabled(false);//滑动取消
//        mRecyclerView.setLayoutManager(new GridLayoutManager(
//                mRecyclerView.getContext(), 4));
//
//        List<String> banners = new ArrayList<>();
//        String bannerImg = "http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg";
//        banners.add(bannerImg);
//        banners.add(bannerImg);
//        banners.add(bannerImg);
//        banners.add(bannerImg);
//        banners.add(bannerImg);
//
//        SimpleImageAdapter enterAdapter = new SimpleImageAdapter(context, banners);
//        mRecyclerView.setAdapter(enterAdapter);
//
//
//    }
    public void initCommont(CommentModel commentModel) {
        if (commentModel == null) {
            LinearLayout ll_comment = $(R.id.ll_comment);
            ll_comment.setVisibility(View.GONE);
            return;
        }

        LinearLayout ll_comment_title = $(R.id.ll_comment_title);
        ImageView comment_head = $(R.id.commont_head);
        TextView tv_comment_title_score = $(R.id.tv_comment_title_score);
        TextView tv_comment_title_count = $(R.id.tv_comment_title_count);

        ImageView commont_head = $(R.id.commont_head);
        TextView commont_name = $(R.id.commont_name);
        TextView commont_time = $(R.id.commont_time);
        TextView commont_score = $(R.id.commont_score);
        TextView tv_comment_content = $(R.id.tv_comment_content);

        ll_comment_title.setOnClickListener(this);
        GlideUtil.LoadCircleImage(context, commentModel.getImg(), comment_head);
        tv_comment_title_score.setText("5.0");
        tv_comment_title_count.setText("(6655条评论)");

        GlideUtil.LoadCircleImage(context, commentModel.getImg(), commont_head);
        commont_name.setText(commentModel.getName());
        commont_time.setText(commentModel.getUserContent());
        commont_score.setText("" + commentModel.getScore());
        tv_comment_content.setText("阿里斯顿库工具奥利给阿数量点击阿里斯顿控件");


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
    protected void onResume() {
        super.onResume();
        convenientBanner.startTurning(Constant.BANNER_RUNNING_TIME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        convenientBanner.stopTurning();
    }

    @Override
    public void initData() {

        mPresenter = new GuideDetailPresenter(context, this);
        mPresenter.guideFindGuideDetails(Constant.DEFAULT_CITY, guideId);
        mPresenter.bannerFindByType(Constant.BANNER_GUIDE, guideId);

        final int mDisplayHeight = AppUtils.getDisplayHeight();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > mDisplayHeight) {
                    tv_back_top.setVisibility(View.VISIBLE);
                } else {
                    tv_back_top.setVisibility(View.GONE);
                }
            }
        });
    }

    //initInfo
    public void initInfo(GuideDetailModel model) {
        //个人信息
        GlideUtil.LoadCircleImage(context, model.getGuideImg(), iv_head);
        tv_name.setText(model.getGuideName());
        my_rating_bar.setRating((int) model.getGuideScore());
        tv_service_num.setText(model.getServiceCounts());
        guideLabel.setTabel(model.getSign());
        stv_tag.setServiceTag(model.getLanguage());

        List<Integer> authentications = new ArrayList<>();
        authentications.add(GuideAuthenticationView.AUTHENT_IDENTITY);
        authentications.add(GuideAuthenticationView.AUTHENT_CAR);
        guide_authentication.setAuthentication(authentications);
        tv_comment_content.setText(model.getIntroduce());

        initCommont(model.getTravelFirstReviewVO());

        webView.loadDataWithBaseURL(null, Constant.HTML_TEST, "text/html", "utf-8", null);

        defaultDialog = new DefaultDialog(context, "123123",
                new DefaultDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                    }
                })
                .setTitle("提示")
                .setNegativeButton("呼叫");

    }

    //banner
    public void initBanner(List<BannerModel> banners) {
        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView(new LocalImageHolderView.BannerListener<BannerModel>() {

                    @Override
                    public void bannerListener(Context context, int position, BannerModel data, ImageView view) {
                        GlideUtil.LoadImage(context, data.getImgUrl(), view);
                    }
                });
            }
        }, banners)
                .setPointViewVisible(true) //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.oval_white_hollow, R.drawable.oval_theme_solid})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
//                    .setOnItemClickListener(this) //设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）
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
            case R.id.ll_comment_title:
                startActivity(new Intent(context, CommentDetailActivity.class));
                break;
            case R.id.btn_call:
                if(defaultDialog == null) return;
                defaultDialog.show();
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
            case R.id.tv_back_top:
                scrollView.scrollTo(0, 0);
                break;

        }
    }

    private void showPopService() {
        if (popService == null) {
            popService = new PopService(activity, btn_submit);
        }
        popService.showPopupWindow(btn_submit);
    }

    @Override
    public void bannerFindByTypeSuccess(List<BannerModel> models) {
        initBanner(models);
    }

    @Override
    public void bannerFindByTypeFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void guideFindGuideDetailsSuccess(GuideDetailModel model) {
        initInfo(model);
    }

    @Override
    public void guideFindGuideDetailsFailed(String msg) {
        showShortToast(msg);
    }
}
