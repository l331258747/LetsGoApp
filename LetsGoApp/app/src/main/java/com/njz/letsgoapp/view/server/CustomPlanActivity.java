package com.njz.letsgoapp.view.server;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.CustomPlanModel;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.server.CustomPlanContract;
import com.njz.letsgoapp.mvp.server.CustomPlanPresenter;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.view.serverFragment.CustomOffersFragment;
import com.njz.letsgoapp.view.serverFragment.CustomTripFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/20
 * Function:
 */

public class CustomPlanActivity extends BaseActivity implements CustomPlanContract.View, View.OnClickListener {

    TextView tv_title,tv_time_content,tv_num,tv_finish_price,tv_phone,tv_pay;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    CustomPlanPresenter planPresenter;

    public String[] titles = {"行程介绍", "报价明细"};
    public List<Fragment> mFragments;

    int orderId;
    String guidePhone;
    boolean showPay;
    PayModel payModel;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID",0);
        guidePhone = intent.getStringExtra("GUIDE_PHONE");
        showPay = intent.getBooleanExtra("SHOW_PAY",false);
        payModel = intent.getParcelableExtra("PAY_MODEL");
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
        tv_pay = $(R.id.tv_pay);

        tv_phone.setOnClickListener(this);
        tv_pay.setOnClickListener(this);

        if(showPay)
            tv_pay.setVisibility(View.VISIBLE);
        else
            tv_pay.setVisibility(View.GONE);
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
        tv_finish_price.setText("￥" + model.getServePrice());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_phone:
                DialogUtil.getInstance().showGuideMobileDialog(context,guidePhone);
                break;
            case R.id.tv_pay:
                if(payModel == null)
                    showShortToast("payModel == null");
                PayActivity.startActivity(context, payModel);
                break;
        }
    }
}
