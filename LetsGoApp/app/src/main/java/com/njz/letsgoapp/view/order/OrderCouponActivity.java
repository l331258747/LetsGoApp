package com.njz.letsgoapp.view.order;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderCouponAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.mine.CouponData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class OrderCouponActivity extends BaseActivity {

    RelativeLayout rl_nonuse;
    ImageView iv_nonuse;
    RecyclerView recyclerView;

    OrderCouponAdapter mAdapter;

    List<CouponData> datas;

    int positon;

    @Override
    public void getIntentData() {
        super.getIntentData();
        positon = intent.getIntExtra("position",-1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_order;
    }

    @Override
    public void initView() {
        showLeftAndTitle("我的优惠卷");
        rl_nonuse = $(R.id.rl_nonuse);
        iv_nonuse = $(R.id.iv_nonuse);

        initRecycler();

        rl_nonuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNonuse(-1);
            }
        });
    }

    public void setNonuse(int position) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setSelected(false);
        }
        if (position != -1) {
            datas.get(position).setSelected(true);
            iv_nonuse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.oval_f7_99_r40));
        } else {
            iv_nonuse.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.oval_f7_theme_r40));
        }
        mAdapter.notifyDataSetChanged();
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
        mAdapter = new OrderCouponAdapter(activity, new ArrayList<CouponData>());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OrderCouponAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setNonuse(position);
            }
        });
    }

    public void getData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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

        setNonuse(positon);
    }
}
