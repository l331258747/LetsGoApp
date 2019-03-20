package com.njz.letsgoapp.view.server;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.bean.server.ServerItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.constant.URLConstant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.map.MapActivity;
import com.njz.letsgoapp.mvp.other.BannerContract;
import com.njz.letsgoapp.mvp.other.BannerPresenter;
import com.njz.letsgoapp.mvp.server.ServerDetailContract;
import com.njz.letsgoapp.mvp.server.ServerDetailPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServerDetailEvent;
import com.njz.letsgoapp.util.rxbus.busEvent.ServerPriceTotalEvent;
import com.njz.letsgoapp.util.rxbus.busEvent.ServerSelectedEvent;
import com.njz.letsgoapp.view.im.ChatActivity;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.serverFragment.ServerEvaluateFragment;
import com.njz.letsgoapp.view.serverFragment.ServerFeatureFragment;
import com.njz.letsgoapp.widget.PriceView;
import com.njz.letsgoapp.widget.popupwindow.PopServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceDetailActivity extends BaseActivity implements ServerDetailContract.View, BannerContract.View, View.OnClickListener {

    public static final String TITLE = "TITLE";
    public static final String SERVICEID = "SERVICEID";
    public static final String SERVER_ITEM = "SERVER_ITEM";

    public ConvenientBanner convenientBanner;
    public TextView tv_title, tv_sell, tv_submit, tv_phone, tv_back_top, tv_float_call,tv_consult;
    public TextView tv_destination, tv_destination2;
    public PriceView pv_price;
    public ViewPager mViewPager;

    public String[] titles = {"服务特色", "TA的评价"};
    public List<Fragment> mFragments;
    public TabLayout mTabLayout;

    public String title;
    public int serviceId;
    public ServerItem serverItem;
    public boolean isCustom;
    public boolean isHideBottom;

    public ServerDetailPresenter serverDetailPresenter;
    public BannerPresenter bannerPresenter;
    public ServerDetailModel model;

    public LinearLayout ll_bottom;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra(TITLE);
        serviceId = intent.getIntExtra(SERVICEID, 0);
        serverItem = intent.getParcelableExtra(SERVER_ITEM);

        isCustom = intent.getBooleanExtra("isCustom",false);
        isHideBottom = intent.getBooleanExtra("isHideBottom", false);
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
    }

    public void initViewPage(ServerDetailModel model) {
        mFragments = new ArrayList<>();
        mFragments.add(ServerFeatureFragment.newInstance(model));
        mFragments.add(ServerEvaluateFragment.newInstance(model.getGuideId(),model.getId(),model.getScore(),model.getReviewCount()));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void initView() {
        showLeftAndTitle(title + "详情介绍");
        showRightIv();
        getRightIv().setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(), R.mipmap.icon_share));
        getRightIv().setOnClickListener(this);

        tv_consult = $(R.id.tv_consult);
        mViewPager = $(R.id.viewpager);
        tv_float_call = $(R.id.tv_float_call);
        mTabLayout = $(R.id.tablayout);
        ll_bottom = $(R.id.ll_bottom);
        convenientBanner = $(R.id.convenientBanner);
        tv_title = $(R.id.tv_title);
        tv_phone = $(R.id.tv_phone);
        tv_destination = $(R.id.tv_destination);
        tv_destination2 = $(R.id.tv_destination2);
        tv_sell = $(R.id.tv_score);
        pv_price = $(R.id.pv_price);
        tv_submit = $(R.id.tv_submit);
        tv_back_top = $(R.id.tv_back_top);
        tv_back_top.setVisibility(View.GONE);

        tv_submit.setOnClickListener(this);
        tv_destination2.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_back_top.setOnClickListener(this);
        tv_float_call.setOnClickListener(this);
        tv_consult.setOnClickListener(this);

        if (isHideBottom) {
            tv_submit.setVisibility(View.INVISIBLE);
        } else {
            tv_submit.setVisibility(View.VISIBLE);
        }
        initBottom();
    }

    public void initBottom(){
        ll_bottom.setVisibility(View.VISIBLE);
        tv_float_call.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        convenientBanner.stopTurning();
    }

    @Override
    protected void onResume() {
        super.onResume();
        convenientBanner.startTurning(Constant.BANNER_RUNNING_TIME);//设置自动切换（同时设置了切换时间间隔）
    }

    @Override
    public void initData() {
        if(isCustom){
            tv_submit.setText("立即定制");
        }

        serverDetailPresenter = new ServerDetailPresenter(context, this);
        bannerPresenter = new BannerPresenter(context, this);

        serverDetailPresenter.serveGuideServeOrder(serviceId);
        bannerPresenter.bannerFindByType(0, serviceId);

    }

    public void initDetail(ServerDetailModel model) {
        tv_title.setText(model.getTitle());
        if (TextUtils.isEmpty(model.getAddress())) {
            tv_destination.setVisibility(View.GONE);
        } else {
            tv_destination.setText(model.getAddress());
            tv_destination.setVisibility(View.VISIBLE);
        }

        StringUtils.setHtml(tv_destination2, getResources().getString(R.string.destination2));

        tv_sell.setText("已售:" + model.getSellCount());
        pv_price.setPrice(model.getServePrice());

        initViewPage(model);

        //预订(￥0)
//        tv_submit.setText("预订(￥" + model.getServePrice() +")");

    }


    public void initBanner(List<BannerModel> models) {
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
        }, models)
                .setPointViewVisible(true) //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.oval_white_hollow, R.drawable.oval_theme_solid})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
//                    .setOnItemClickListener(this) //设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）
    }

    PopServer popServer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_submit:
//                if (popServer == null) {
//                    popServer = new PopServer(activity, tv_submit, model,null);
//                }
//                popServer.showPopupWindow(tv_submit);
//                break;
            case R.id.tv_submit:
                if(model == null) return;

                if(!MySelfInfo.getInstance().isLogin()){
                    startActivity(new Intent(context,LoginActivity.class));
                    return ;
                }

                if(model.getServeType() == Constant.SERVER_TYPE_CUSTOM_ID){
                    Intent intent = new Intent(context, CustomActivity.class);
                    intent.putExtra("LOCATION", model.getAddress());
                    intent.putExtra("GUIDE_ID", model.getGuideId());
                    intent.putExtra("SERVER_ID", model.getId());

                    startActivity(intent);
                    return;
                }
                if (popServer == null) {
                    popServer = new PopServer(activity, tv_submit, model, serverItem);
                    popServer.setSubmit("选好了", new PopServer.SubmitClick() {
                        @Override
                        public void onClick(ServerItem serverItem) {
                            RxBus2.getInstance().post(new ServerSelectedEvent(serverItem));
                            finish();
                        }
                    });
                }
                popServer.showPopupWindow(tv_submit);
                break;
            case R.id.tv_phone:
                if (model == null) return;
                DialogUtil.getInstance().showGuideMobileDialog(context, model.getMobile(),0,model.getId(),model.getGuideId());
                break;
            case R.id.tv_destination2:
                startActivity(new Intent(context, MapActivity.class));
                break;
            case R.id.tv_back_top:
//                scrollView.scrollTo(0, 0);
                break;
            case R.id.tv_float_call:
                if (model == null) return;
                DialogUtil.getInstance().showGuideMobileDialog(context, model.getMobile(),0,model.getId(),model.getGuideId());
                break;
            case R.id.right_iv:
                if (model == null) return;
                ShareDialog dialog = new ShareDialog(activity,
                        model.getAddress()+model.getServerName() + "服务",
                        model.getTitle(),
                        model.getTitleImg2(),
                        URLConstant.SHARE_SERVER+"?id="+model.getId());
                dialog.setReportData(model.getGuideId(), ShareDialog.REPORT_SERVICE,model.getId());
                dialog.setType(ShareDialog.TYPE_ALL);
                dialog.show();
                break;
            case R.id.tv_consult:
                if (!MySelfInfo.getInstance().isLogin()) {//登录状态
                    startActivity(new Intent(context,LoginActivity.class));
                    return ;
                }

                if(!MySelfInfo.getInstance().getImLogin()){
                    showShortToast("用户未注册到im");
                    return;
                }

                if(model == null) return;
                String name = "g_"+ model.getGuideId();
                String myName = EMClient.getInstance().getCurrentUser();
                if (!TextUtils.isEmpty(name)) {
                    if (name.equals(myName)) {
                        showShortToast("不能和自己聊天");
                        return;
                    }
                    Intent chat = new Intent(context, ChatActivity.class);
                    chat.putExtra(EaseConstant.EXTRA_USER_ID, name);  //对方账号
                    chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
                    chat.putExtra(EaseConstant.EXTRA_USER_NAME, model.getName());
                    startActivity(chat);

                } else {
                    showShortToast("导游还未注册即时通讯，请使用电话联系TA");
                }
                break;
        }
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
    public void serveGuideServeOrderSuccess(ServerDetailModel str) {
        this.model = str;
        initDetail(model);
    }

    @Override
    public void serveGuideServeOrderFailed(String msg) {
        showShortToast(msg);
    }
}
