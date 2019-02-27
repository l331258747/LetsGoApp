package com.njz.letsgoapp.view.coupon;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.coupon.CouponReceiveAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.coupon.CouponModel;
import com.njz.letsgoapp.bean.coupon.CouponReceiveModel;
import com.njz.letsgoapp.constant.URLConstant;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.mvp.coupon.ActivityReceiveContract;
import com.njz.letsgoapp.mvp.coupon.ActivityReceivePresenter;
import com.njz.letsgoapp.mvp.coupon.ActivityReceiveSubmitContract;
import com.njz.letsgoapp.mvp.coupon.ActivityReceiveSubmitPresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class CouponReceiveActivity extends BaseActivity implements ActivityReceiveContract.View, ActivityReceiveSubmitContract.View {

    TextView tv_rule, tv_submit;
    RecyclerView recyclerView;
    ImageView iv_img;

    CouponReceiveAdapter mAdapter;
    ActivityReceivePresenter mPresenter;
    ActivityReceiveSubmitPresenter submitPresenter;

    int eventId;
    CouponReceiveModel model;

    @Override
    public void getIntentData() {
        super.getIntentData();
        eventId = intent.getIntExtra("eventId", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_receive;
    }

    @Override
    public void initView() {
        showLeftAndTitle("活动领取");
        showRightIv();
        getRightIv().setImageDrawable(ContextCompat.getDrawable(AppUtils.getContext(), R.mipmap.icon_share));
        getRightIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model == null) return;
                ShareDialog dialog = new ShareDialog(activity, model.getTitle(), model.getRule(), model.getImage()
                        , URLConstant.SHARE_ACTIVITY + "?eventId=" + model.getId());
                dialog.setType(ShareDialog.TYPE_FRIEND);
                dialog.show();
            }
        });

        tv_rule = $(R.id.tv_rule);
        tv_submit = $(R.id.tv_submit);
        iv_img = $(R.id.iv_img);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MySelfInfo.getInstance().isLogin()) {
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                submitPresenter.userCouponPublish(eventId);
            }
        });

        initRecycler();
    }

    @Override
    public void initData() {
        mPresenter = new ActivityReceivePresenter(context, this);
        submitPresenter = new ActivityReceiveSubmitPresenter(context, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CouponReceiveAdapter(activity, new ArrayList<CouponModel>());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
    }

    public void getData() {
        mPresenter.userCouponInfo(eventId);
    }

    @Override
    public void userCouponInfoSuccess(CouponReceiveModel model) {
        if (model == null) return;
        this.model = model;
        mAdapter.setData(model.getCouponList());
        GlideUtil.LoadImage(context, model.getImage(), iv_img);
        tv_rule.setText(model.getRule());

        if (model.getIsShare() == 0) {
            getRightIv().setVisibility(View.GONE);
        } else {
            getRightIv().setVisibility(View.VISIBLE);
        }
        showLeftAndTitle(model.getTitle());

        setSubmit(model.getReceiveStatus() == 1 ? true : false);
    }

    public void setSubmit(boolean b) {
        if (b) {
            tv_submit.setText("已领取");
            tv_submit.setEnabled(false);
            tv_submit.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gray_solid_r5));
        } else {
            tv_submit.setText("立即领取");
            tv_submit.setEnabled(true);
            tv_submit.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_theme_solid_r5));
        }
    }

    @Override
    public void userCouponInfoFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void userCouponPublishSuccess(String str) {
        showShortToast(str);
        setSubmit(true);
    }

    @Override
    public void userCouponPublishFailed(String msg) {
        showShortToast(msg);
    }
}
