package com.njz.letsgoapp.view.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.order.ServiceRefundRuleModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.map.MapActivity;
import com.njz.letsgoapp.mvp.home.ServiceDetailContract;
import com.njz.letsgoapp.mvp.home.ServiceDetailPresenter;
import com.njz.letsgoapp.mvp.order.ServiceRefundRuleContract;
import com.njz.letsgoapp.mvp.order.ServiceRefundRulePresenter;
import com.njz.letsgoapp.mvp.other.BannerContract;
import com.njz.letsgoapp.mvp.other.BannerPresenter;
import com.njz.letsgoapp.mvp.server.ServerDetailContract;
import com.njz.letsgoapp.mvp.server.ServerDetailPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServiceDetailCloseEvent;
import com.njz.letsgoapp.util.webview.LWebView;
import com.njz.letsgoapp.view.home.serverFragment.ServerEvaluateFragment;
import com.njz.letsgoapp.view.home.serverFragment.ServerFeatureFragment;
import com.njz.letsgoapp.view.home.serverFragment.ServerOtherFragment;
import com.njz.letsgoapp.view.order.OrderListFragment;
import com.njz.letsgoapp.view.order.OrderRefundListFragment;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.PriceView;
import com.njz.letsgoapp.widget.ServiceTagView;
import com.njz.letsgoapp.widget.emptyView.EmptyView3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceDetailActivity extends BaseActivity implements ServerDetailContract.View,BannerContract.View,View.OnClickListener {

    public static final String TITLE = "TITLE";
    public static final String SERVICEID = "SERVICEID";
    public static final String SERVICEITEMS = "SERVICEITEMS";

    public ConvenientBanner convenientBanner;
    public TextView tv_title,  tv_sell, tv_submit,  tv_phone, tv_back_top;
    public TextView tv_destination,tv_destination2;
    public PriceView pv_price;
    public ViewPager mViewPager;

    public String[] titles = {"服务特色", "TA的评价"};
    public List<Fragment> mFragments;
    public TabLayout mTabLayout;

    public String title;
    public int serviceId;
    public List<ServiceItem> serviceItems;
    public boolean isHideBottom;

    public ServerDetailPresenter serverDetailPresenter;
    public BannerPresenter bannerPresenter;
    public ServerDetailMedel model;

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
        serviceItems = intent.getParcelableArrayListExtra(SERVICEITEMS);
        isHideBottom = intent.getBooleanExtra("isHideBottom",false);
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
    }

    public  void initViewPage(ServerDetailMedel model){
        mFragments = new ArrayList<>();
        mFragments.add(ServerFeatureFragment.newInstance(model));
        mFragments.add(ServerEvaluateFragment.newInstance());

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

        mViewPager = $(R.id.viewpager);
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

        if(isHideBottom){
            tv_submit.setVisibility(View.INVISIBLE);
        }else{
            tv_submit.setVisibility(View.VISIBLE);
        }
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
        serverDetailPresenter = new ServerDetailPresenter(context, this);
        bannerPresenter = new BannerPresenter(context,this);

        serverDetailPresenter.serveGuideServeOrder(serviceId);
        bannerPresenter.bannerFindByType(0,serviceId);

    }

    public void initDetail(ServerDetailMedel model) {
        tv_title.setText(model.getTitle());
        if(TextUtils.isEmpty(model.getAddress())){
            tv_destination.setVisibility(View.GONE);
        }else{
            tv_destination.setText(model.getAddress());
            tv_destination.setVisibility(View.VISIBLE);
        }

        StringUtils.setHtml(tv_destination2, getResources().getString(R.string.destination2));

        tv_sell.setText("已售:" + model.getSellCount());
        pv_price.setPrice(model.getServePrice());

        initViewPage(model);

        //预订(￥0)
//        tv_submit.setText("预订(￥" + model.getServePrice() +")");

        setServiceSelected();
    }

    public void setServiceSelected(){
        if(serviceItems == null) return;
        for (ServiceItem item : serviceItems){
            if(item.getId() == model.getId()){
                tv_submit.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_cc_solid_r5_p8));
                tv_submit.setTextColor(ContextCompat.getColor(context,R.color.color_text));
                tv_submit.setEnabled(false);
            }
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
//                ServiceItem data = new ServiceItem();
//                data.setServiceType(model.getServiceType());
//                data.setValue(model.getValue());
//                data.setServeType(model.getServeType());
//                data.setId(model.getId());
//                data.setTitile(model.getTitle());
//                data.setPrice(model.getServePrice());
//                data.setImg(model.getTitleImg());
//                RxBus2.getInstance().post(data);
//                RxBus2.getInstance().post(new ServiceDetailCloseEvent());
//                finish();
                break;
            case R.id.tv_phone:
                if(model == null) return;
                DialogUtil.getInstance().showGuideMobileDialog(context,model.getMobile());
                break;
            case R.id.tv_destination2:
                startActivity(new Intent(context, MapActivity.class));
                break;
            case R.id.tv_back_top:
//                scrollView.scrollTo(0, 0);
                break;
            case R.id.right_iv:
                if(model == null) return;
                ShareDialog dialog = new ShareDialog(activity,"","","","");
                dialog.setReportData(model.getId(), ShareDialog.REPORT_SERVICE);
                dialog.setType(ShareDialog.TYPE_REPORT);
                dialog.show();
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
    public void serveGuideServeOrderSuccess(ServerDetailMedel str) {
        this.model = str;
        initDetail(model);
    }

    @Override
    public void serveGuideServeOrderFailed(String msg) {
        showShortToast(msg);
    }
}
