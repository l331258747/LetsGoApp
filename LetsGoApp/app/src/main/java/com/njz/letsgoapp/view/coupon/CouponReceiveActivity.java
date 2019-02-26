package com.njz.letsgoapp.view.coupon;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.coupon.CouponReceiveAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.coupon.CouponData;
import com.njz.letsgoapp.dialog.ActivityDialog;
import com.njz.letsgoapp.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class CouponReceiveActivity extends BaseActivity {

    TextView tv_time, tv_rule, tv_limit, tv_tip, tv_submit;
    RecyclerView recyclerView;
    ImageView iv_img;

    CouponReceiveAdapter mAdapter;
    List<CouponData> datas;


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
                ActivityDialog dialog = new ActivityDialog(activity);
                dialog.show();
            }
        });

        tv_time = $(R.id.tv_time);
        tv_rule = $(R.id.tv_rule);
        tv_limit = $(R.id.tv_limit);
        tv_tip = $(R.id.tv_tip);
        tv_submit = $(R.id.tv_submit);
        iv_img = $(R.id.iv_img);

        initRecycler();
    }

    @Override
    public void initData() {
        getData();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CouponReceiveAdapter(activity, new ArrayList<CouponData>());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
    }

    public void getData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CouponData data = new CouponData();
            data.setTitle("100元优惠卷【新用户注册】");
            data.setPrice(100);
            data.setLimit(1000);
            data.setExpire("2019-05-05");
            data.setRule("规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则规则");
            data.setType(0);
            data.setExpire(true);
            datas.add(data);
        }
        mAdapter.setData(datas);

        iv_img.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_coupon_activity));
    }
}
