package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.constant.URLConstant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.mvp.home.GuideDetailContract;
import com.njz.letsgoapp.mvp.home.GuideDetailPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServerPriceTotalEvent;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.server.OrderSubmitActivity;
import com.njz.letsgoapp.view.serverFragment.ServerBookRuleFragment;
import com.njz.letsgoapp.view.serverFragment.ServerEvaluateFragment;
import com.njz.letsgoapp.view.serverFragment.ServerListFragment;
import com.njz.letsgoapp.view.serverFragment.ServerStoryFragment;
import com.njz.letsgoapp.widget.GuideAuthenticationView;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;
import com.njz.letsgoapp.widget.popupwindow.PopServerDetail;
import com.njz.letsgoapp.widget.myTabLayout.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/9
 * Function: 导游详情
 */

public class GuideDetailActivity extends BaseActivity implements View.OnClickListener, GuideDetailContract.View {

    ConvenientBanner convenientBanner;
    ImageView iv_head,iv_sex;
    TextView tv_name, tv_service_num, tv_comment_content, tv_content, tv_back_top, btn_submit,tv_price_total;
    MyRatingBar my_rating_bar;
    ServiceTagView stv_tag;

//    LinearLayout ll_select_service;
    LinearLayout btn_call,ll_detail,ll_bottom;

    GuideLabelView guideLabel;
    GuideAuthenticationView guide_authentication;

    GuideDetailPresenter mPresenter;

    GuideDetailModel guideDetailModel;
    PopServerDetail popServerDetail;

    int guideId;
    public static final String GUIDEID = "GUIDEID";

//    EmptyView3 view_empty;

    public String[] titles = {"TA的服务", "TA的评价","TA的故事","预订须知"};
    public List<Fragment> mFragments;
    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    public List<ServerItem> serverItems;

    public  Disposable serverPriceTotalDisposable;

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

        mViewPager = $(R.id.viewpager);
        tv_price_total = $(R.id.tv_price_total);
        mTabLayout = $(R.id.tablayout);
        convenientBanner = $(R.id.convenientBanner);
        iv_head = $(R.id.iv_head);
        iv_sex = $(R.id.iv_sex);
        tv_name = $(R.id.tv_name);
        my_rating_bar = $(R.id.my_rating_bar);
        stv_tag = $(R.id.stv_tag);
        tv_content = $(R.id.tv_content);
        btn_call = $(R.id.btn_call);
        ll_detail = $(R.id.ll_detail);
        ll_bottom = $(R.id.ll_bottom);
        btn_submit = $(R.id.btn_submit);
        guideLabel = $(R.id.guide_label);
        tv_service_num = $(R.id.tv_service_num);
        guide_authentication = $(R.id.guide_authentication);
        tv_comment_content = $(R.id.tv_comment_content);
        tv_back_top = $(R.id.tv_back_top);

        tv_back_top.setVisibility(View.GONE);
        tv_back_top.setOnClickListener(this);

        btn_call.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        ll_detail.setOnClickListener(this);
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
        mPresenter.guideFindGuideDetails(MySelfInfo.getInstance().getDefaultCity(), guideId);
        mPresenter.bannerFindByType(Constant.BANNER_GUIDE, guideId);

        serverItems = new ArrayList<>();

        serverPriceTotalDisposable = RxBus2.getInstance().toObservable(ServerPriceTotalEvent.class, new Consumer<ServerPriceTotalEvent>() {
            @Override
            public void accept(ServerPriceTotalEvent serverPriceTotalEvent) throws Exception {
                tv_price_total.setText("￥" + getServerPriceTotal());
            }
        });
    }

    //认证
    public List<Integer> getViable(GuideDetailModel model){
        List<Integer> authentications = new ArrayList<>();

        if(model.getDriveViable() == 2){
            authentications.add(GuideAuthenticationView.AUTHENT_CAR);
        }
        if(model.getGuideViable() == 2){
            authentications.add(GuideAuthenticationView.AUTHENT_GUIDE);
        }
        if(model.getCardViable() == 2){
            authentications.add(GuideAuthenticationView.AUTHENT_IDENTITY);
        }
        return authentications;
    }

    //initInfo
    public void initInfo(final GuideDetailModel model) {
        //个人信息
        GlideUtil.LoadCircleImage(context, model.getGuideImg(), iv_head);
        iv_sex.setImageDrawable(ContextCompat.getDrawable(context,model.getGuideGender() == 2?R.mipmap.icon_girl:R.mipmap.icon_boy));
        tv_name.setText(model.getGuideName());
        my_rating_bar.setRating((int) model.getGuideScore());
        tv_service_num.setText(model.getServiceCounts());
        guideLabel.setTabel(model.getSign());
        stv_tag.setServiceTag(model.getServiceTag());
        guide_authentication.setAuthentication(getViable(model));
        tv_content.setText(model.getIntroduce());

        initViewPage(model);

    }

    public  void initViewPage(GuideDetailModel model){
        mFragments = new ArrayList<>();
        mFragments.add(ServerListFragment.newInstance(model,serverItems));
        mFragments.add(ServerEvaluateFragment.newInstance());
        mFragments.add(ServerStoryFragment.newInstance(model.getGuideStory()));
        mFragments.add(ServerBookRuleFragment.newInstance());

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_comment_title:
                if(guideDetailModel == null) return;
                intent = new Intent(context, EvaluateListActivity.class);
                intent.putExtra(EvaluateListActivity.GUIDEID,guideDetailModel.getId());
                startActivity(intent);
                break;
            case R.id.btn_call:
                if(guideDetailModel == null) return;
                DialogUtil.getInstance().showGuideMobileDialog(context,guideDetailModel.getMobile());
                break;
            case R.id.ll_select_service:
            case R.id.btn_submit:
                if (!MySelfInfo.getInstance().isLogin()) {//登录状态
                    startActivity(new Intent(context,LoginActivity.class));
                    return ;
                }
//                showPopService();
                //TODO 结算界面

                if(serverItems.size() == 0){
                    showShortToast("请选择服务");
                    return;
                }

                intent = new Intent(context,OrderSubmitActivity.class);
                intent.putParcelableArrayListExtra("SERVICEMODEL", (ArrayList<ServerItem>) serverItems);
                intent.putExtra("GUIDE_ID",guideDetailModel.getId());
                intent.putExtra("LOCATION",MySelfInfo.getInstance().getDefaultCity());
                startActivity(intent);

                break;
            case R.id.right_iv:
                if(guideDetailModel == null) return;
                ShareDialog dialog = new ShareDialog(activity,
                        guideDetailModel.getGuideName()+"向导",
                        TextUtils.isEmpty(guideDetailModel.getIntroduce())?"赶快约TA一起体验当地的风土人情吧！":guideDetailModel.getIntroduce(),
                        guideDetailModel.getGuideImg(),
                        URLConstant.SHARE_GUIDE+"?location="+MySelfInfo.getInstance().getDefaultCity()+"&guideId="+guideDetailModel.getId());
                dialog.setReportData(guideDetailModel.getId(), ShareDialog.REPORT_GUIDE);
                dialog.setType(ShareDialog.TYPE_ALL);
                dialog.show();
                break;
            case R.id.tv_back_top:
//                scrollView.scrollTo(0, 0);
                break;
            case R.id.ll_detail:
                if(serverItems.size() == 0){
                    showShortToast("请选择服务");
                    return;
                }
                popServerDetail = new PopServerDetail(activity, ll_bottom,serverItems);
                popServerDetail.showPopupWindow(ll_bottom);
                break;

        }
    }

    public float getServerPriceTotal(){
        float serverPriceTotal = 0;
        for (int i=0; i < serverItems.size();i++){
            serverPriceTotal = serverPriceTotal + (serverItems.get(i).getPrice() * serverItems.get(i).getServeNum());
        }
        return serverPriceTotal;
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
        guideDetailModel = model;
        initInfo(model);
    }

    @Override
    public void guideFindGuideDetailsFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serverPriceTotalDisposable !=null && !serverPriceTotalDisposable.isDisposed())
            serverPriceTotalDisposable.dispose();
    }
}
