package com.njz.letsgoapp.view.coupon;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.coupon.OrderCouponAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.coupon.CouponModel;
import com.njz.letsgoapp.bean.coupon.OrderCouponModel;
import com.njz.letsgoapp.mvp.coupon.OrderCouponContract;
import com.njz.letsgoapp.mvp.coupon.OrderCouponPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.OrderCouponEvent;
import com.njz.letsgoapp.widget.emptyView.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/25
 * Function:
 */

public class OrderCouponActivity extends BaseActivity implements OrderCouponContract.View{

    RelativeLayout rl_nonuse;
    ImageView iv_nonuse;
    RecyclerView recyclerView;

    OrderCouponAdapter mAdapter;

    List<CouponModel> datas;

    int couponId;

    OrderCouponPresenter mPresenter;

    float totalOrderPrice;

    EmptyView view_empty;

    @Override
    public void getIntentData() {
        super.getIntentData();
        couponId = intent.getIntExtra("couponId",-1);
        totalOrderPrice = intent.getFloatExtra("totalOrderPrice",-1);
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
        view_empty = $(R.id.view_empty);

        initRecycler();

        rl_nonuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNonuse(-1);
                RxBus2.getInstance().post(new OrderCouponEvent(-1,0));
                finish();
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
        mPresenter = new OrderCouponPresenter(context,this);
        getData(totalOrderPrice);
    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderCouponAdapter(activity, new ArrayList<CouponModel>());
        recyclerView.setAdapter(mAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        mAdapter.setOnItemClickListener(new OrderCouponAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setNonuse(position);
                RxBus2.getInstance().post(new OrderCouponEvent(datas.get(position).getId(),datas.get(position).getTypeMoney()));
                finish();
            }
        });
    }

    public void getData(float totalOrderPrice) {
        mPresenter.userCouponChooseCoupon(totalOrderPrice);
    }

    @Override
    public void userCouponChooseCouponSuccess(OrderCouponModel models) {
        if(models == null) return;
        datas = models.getUserCouponVo();
        mAdapter.setData(datas);

        for (int i=0;i<datas.size();i++){
            if(datas.get(i).getId() == couponId){
                setNonuse(i);
            }
        }

        if(datas.size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_discount,"暂无可用优惠卷~");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void userCouponChooseCouponFailed(String msg) {
        showShortToast(msg);
    }
}
