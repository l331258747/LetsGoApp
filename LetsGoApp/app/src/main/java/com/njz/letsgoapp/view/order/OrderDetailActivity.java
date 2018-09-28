package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.mine.MyCommentAdapter;
import com.njz.letsgoapp.adapter.order.OrderDetailAdapter;
import com.njz.letsgoapp.adapter.order.OrderWaitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.bean.order.OrderModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.order.OrderDetailContract;
import com.njz.letsgoapp.mvp.order.OrderDetailPresenter;
import com.njz.letsgoapp.widget.FixedItemEditView;
import com.njz.letsgoapp.widget.FixedItemTextView;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderDetailContract.View {

    private TextView tv_guide_name, tv_order_status;

    private FixedItemTextView fixed_view_city;
    private FixedItemEditView login_view_name, login_view_phone;
    private EditText et_special;

    private RecyclerView recyclerView;
    private TextView tv_order_price;

    private LinearLayout ll_order_no, ll_order_create_time, ll_order_pay_time, ll_order_pay_method, ll_order_guide_time;
    private TextView tv_order_no, tv_order_create_time, tv_order_pay_time, tv_order_pay_method, tv_order_guide_time;

    private TextView btn_cancel_order, btn_call_guide, btn_pay;

    private OrderDetailPresenter mPresenter;

    private int orderId;

    private OrderDetailAdapter mAdapter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID", 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("查看订单");

        tv_guide_name = $(R.id.tv_guide_name);
        tv_order_status = $(R.id.tv_order_status);

        fixed_view_city = $(R.id.fixed_view_city);
        login_view_name = $(R.id.login_view_name);
        login_view_phone = $(R.id.login_view_phone);
        et_special = $(R.id.et_special);

        recyclerView = $(R.id.recycler_view);

        tv_order_price = $(R.id.tv_order_price);

        ll_order_no = $(R.id.ll_order_no);
        ll_order_create_time = $(R.id.ll_order_create_time);
        ll_order_pay_time = $(R.id.ll_order_pay_time);
        ll_order_pay_method = $(R.id.ll_order_pay_method);
        ll_order_guide_time = $(R.id.ll_order_guide_time);
        tv_order_no = $(R.id.tv_order_no);
        tv_order_create_time = $(R.id.tv_order_create_time);
        tv_order_pay_time = $(R.id.tv_order_pay_time);
        tv_order_pay_method = $(R.id.tv_order_pay_method);
        tv_order_guide_time = $(R.id.tv_order_guide_time);

        btn_cancel_order = $(R.id.btn_cancel_order);
        btn_call_guide = $(R.id.btn_call_guide);
        btn_pay = $(R.id.btn_pay);

        btn_cancel_order.setOnClickListener(this);
        btn_call_guide.setOnClickListener(this);
        btn_pay.setOnClickListener(this);

        login_view_phone.getEtView().setEnabled(false);
        login_view_name.getEtView().setEnabled(false);
        et_special.setEnabled(false);

        ll_order_no.setVisibility(View.GONE);
        ll_order_create_time.setVisibility(View.GONE);
        ll_order_pay_time.setVisibility(View.GONE);
        ll_order_pay_method.setVisibility(View.GONE);
        ll_order_guide_time.setVisibility(View.GONE);

        initRecycler();

    }

    @Override
    public void initData() {
        mPresenter = new OrderDetailPresenter(context, this);
        mPresenter.orderQueryOrder(orderId);

    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderDetailAdapter(activity, new ArrayList<OrderDetailChildModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel_order:

                break;
            case R.id.btn_call_guide:

                break;
            case R.id.btn_pay:

                break;
        }

    }

    @Override
    public void orderQueryOrderSuccess(OrderDetailModel str) {
        switch (str.getPayStatus()) {
            case Constant.ORDER_PAY_WAIT:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());
                break;
            case Constant.ORDER_PAY_ALREADY:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                ll_order_pay_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());
                tv_order_pay_time.setText(str.getPayTime());
                ll_order_pay_method.setVisibility(View.VISIBLE);
                tv_order_pay_method.setText(str.getPayType());

                if (str.getOrderStatus() != Constant.ORDER_TRAVEL_WAIT) {
                    ll_order_guide_time.setVisibility(View.VISIBLE);
                    tv_order_guide_time.setText(str.getGuideSureTime());
                }

                break;
            case Constant.ORDER_PAY_FINISH:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                ll_order_pay_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());
                tv_order_pay_time.setText(str.getPayType());
                ll_order_pay_method.setVisibility(View.VISIBLE);
                tv_order_pay_method.setText(str.getPayType());
                ll_order_guide_time.setVisibility(View.VISIBLE);
                tv_order_guide_time.setText(str.getGuideSureTime());

                break;
            case Constant.ORDER_PAY_REFUND:

                break;
        }

        tv_order_price.setText(str.getOrderPrice() + "");

        tv_guide_name.setText(str.getGuideName());
        tv_order_status.setText(str.getPayStatusStr());

        fixed_view_city.setContent(str.getLocation());
        login_view_name.setContent(str.getName());
        login_view_phone.setContent(str.getMobile());
        et_special.setText(str.getSpecialRequire());


        mAdapter.setData(str.getNjzChildOrderVOS());


    }

    @Override
    public void orderQueryOrderFailed(String msg) {

    }
}
