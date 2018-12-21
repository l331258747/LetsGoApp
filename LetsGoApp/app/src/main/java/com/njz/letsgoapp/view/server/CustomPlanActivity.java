package com.njz.letsgoapp.view.server;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.server.CustomPlanModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.mvp.server.CustomPlanContract;
import com.njz.letsgoapp.mvp.server.CustomPlanPresenter;
import com.njz.letsgoapp.view.serverFragment.CustomOffersFragment;
import com.njz.letsgoapp.view.serverFragment.CustomTripFragment;
import com.njz.letsgoapp.view.serverFragment.ServerEvaluateFragment;
import com.njz.letsgoapp.view.serverFragment.ServerFeatureFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/20
 * Function:
 */

public class CustomPlanActivity extends BaseActivity implements CustomPlanContract.View{

    TextView tv_title,tv_time_content,tv_num,tv_finish_price,tv_phone;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    CustomPlanPresenter planPresenter;

    public String[] titles = {"行程介绍", "报价明细"};
    public List<Fragment> mFragments;

    int orderId;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID",0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_plan;
    }

    @Override
    public void initView() {
        showLeftAndTitle("查看方案");

        tv_title = $(R.id.tv_title);
        tv_time_content = $(R.id.tv_time_content);
        tv_num = $(R.id.tv_num);
        tv_finish_price = $(R.id.tv_finish_price);
        tv_phone = $(R.id.tv_phone);
        mTabLayout = $(R.id.tablayout);
        mViewPager = $(R.id.viewpager);
    }

    @Override
    public void initData() {
        planPresenter = new CustomPlanPresenter(context,this);
        planPresenter.orderCreateOrderViewPlan(orderId);

    }

    private void initInfo(CustomPlanModel model) {
        tv_title.setText(model.getTitle());
        tv_time_content.setText(model.getTravelDate());
        tv_num.setText(model.getPersonNum());
        tv_finish_price.setText("￥" + model.getBugGet());
    }

    public void initViewPage(CustomPlanModel model) {
        mFragments = new ArrayList<>();
        mFragments.add(CustomTripFragment.newInstance(model.getTravelDesign(),model.getRenegePriceThree(),model.getRenegePriceFive()));
        mFragments.add(CustomOffersFragment.newInstance(model.getOfferDetail()));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void orderCreateOrderViewPlanSuccess(List<CustomPlanModel> model) {
        if(model != null && model.size() == 1){
            initInfo(model.get(0));
            initViewPage(model.get(0));
        }
    }

    @Override
    public void orderCreateOrderViewPlanFailed(String msg) {
        showShortToast(msg);
    }
}
