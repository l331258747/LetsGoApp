package com.njz.letsgoapp.view.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderDetailAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.order.OrderDetailContract;
import com.njz.letsgoapp.mvp.order.OrderDetailPresenter;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.widget.FixedItemEditView;
import com.njz.letsgoapp.widget.FixedItemTextView;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, OrderDetailContract.View {

    public TextView tv_guide_name, tv_order_status;

    public FixedItemTextView fixed_view_city;
    public FixedItemEditView login_view_name, login_view_phone;
    public EditText et_special;

    public RecyclerView recyclerView;
    public TextView tv_order_price;

    public LinearLayout ll_order_no, ll_order_create_time, ll_order_pay_time, ll_order_pay_method, ll_order_guide_time, ll_order_refund_apply, ll_order_refund_verify, ll_order_refund_time;
    public TextView tv_order_no, tv_order_create_time, tv_order_pay_time, tv_order_pay_method, tv_order_guide_time, tv_order_refund_apply, tv_order_refund_verify, tv_order_refund_time;

    public TextView btn_cancel_order, btn_call_guide, btn_pay, btn_refund, btn_delete, btn_call_custom, btn_evaluate;

    public OrderDetailPresenter mPresenter;

    public int orderId;

    public OrderDetailAdapter mAdapter;

    OrderDetailModel model;

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
        ll_order_refund_apply = $(R.id.ll_order_refund_apply);
        ll_order_refund_verify = $(R.id.ll_order_refund_verify);
        ll_order_refund_time = $(R.id.ll_order_refund_time);
        tv_order_no = $(R.id.tv_order_no);
        tv_order_create_time = $(R.id.tv_order_create_time);
        tv_order_pay_time = $(R.id.tv_order_pay_time);
        tv_order_pay_method = $(R.id.tv_order_pay_method);
        tv_order_guide_time = $(R.id.tv_order_guide_time);
        tv_order_refund_apply = $(R.id.tv_order_refund_apply);
        tv_order_refund_verify = $(R.id.tv_order_refund_verify);
        tv_order_refund_time = $(R.id.tv_order_refund_time);

        btn_cancel_order = $(R.id.btn_cancel_order);
        btn_call_guide = $(R.id.btn_call_guide);
        btn_pay = $(R.id.btn_pay);
        btn_refund = $(R.id.btn_refund);
        btn_delete = $(R.id.btn_delete);
        btn_call_custom = $(R.id.btn_call_custom);
        btn_evaluate = $(R.id.btn_evaluate);

        btn_cancel_order.setOnClickListener(this);
        btn_call_guide.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_refund.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_call_custom.setOnClickListener(this);
        btn_evaluate.setOnClickListener(this);

        btn_cancel_order.setVisibility(View.GONE);
        btn_call_guide.setVisibility(View.GONE);
        btn_pay.setVisibility(View.GONE);
        btn_refund.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        btn_call_custom.setVisibility(View.GONE);
        btn_evaluate.setVisibility(View.GONE);

        login_view_phone.getEtView().setEnabled(false);
        login_view_name.getEtView().setEnabled(false);
        et_special.setEnabled(false);

        ll_order_no.setVisibility(View.GONE);
        ll_order_create_time.setVisibility(View.GONE);
        ll_order_pay_time.setVisibility(View.GONE);
        ll_order_pay_method.setVisibility(View.GONE);
        ll_order_guide_time.setVisibility(View.GONE);
        ll_order_refund_apply.setVisibility(View.GONE);
        ll_order_refund_verify.setVisibility(View.GONE);
        ll_order_refund_time.setVisibility(View.GONE);

        initRecycler();

    }

    @Override
    public void initData() {
        mPresenter = new OrderDetailPresenter(context, this);
        mPresenter.orderQueryOrder(orderId);

    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderDetailAdapter(activity, new ArrayList<OrderDetailChildModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_cancel_order:
                intent = new Intent(context,OrderCancelActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                context.startActivity(intent);
                break;
            case R.id.btn_call_guide:
                DialogUtil.getInstance().getDefaultDialog(context, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                        context.startActivity(dialIntent);
                        alterDialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btn_pay:
                PayModel payModel = new PayModel();
                payModel.setTotalAmount(model.getOrderPrice()+"");
                payModel.setSubject(model.getLocation() + model.getGuideName()+"导游为您服务！");
                payModel.setOutTradeNo(model.getOrderNo());
                payModel.setLastPayTime("2018-01-01 12:00");
                PayActivity.startActivity(context, payModel);
                break;
            case R.id.btn_refund:
                startActivity(new Intent(context,OrderRefundActivity.class));
                break;
            case R.id.btn_delete:
                showShortToast("删除");
                break;
            case R.id.btn_call_custom:
                DialogUtil.getInstance().getDefaultDialog(context, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                    @Override
                    public void exectEvent(DialogInterface alterDialog) {
                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                        context.startActivity(dialIntent);
                        alterDialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btn_evaluate:
                intent = new Intent(context,OrderEvaluateActivity.class);
                intent.putExtra("ORDER_ID",model.getId());
                intent.putExtra("GUIDE_ID",model.getId());
                startActivity(intent);
                break;
        }

    }

    @Override
    public void orderQueryOrderSuccess(OrderDetailModel str) {
        model = str;
        switch (str.getPayStatus()) {
            case Constant.ORDER_PAY_WAIT:
                ll_order_no.setVisibility(View.VISIBLE);
                ll_order_create_time.setVisibility(View.VISIBLE);
                tv_order_no.setText(str.getOrderNo());
                tv_order_create_time.setText(str.getCreateTime());

                btn_cancel_order.setVisibility(View.VISIBLE);
                btn_call_guide.setVisibility(View.VISIBLE);
                btn_pay.setVisibility(View.VISIBLE);

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

                if (str.getOrderStatus() != Constant.ORDER_TRAVEL_GOING) {
                    btn_call_custom.setVisibility(View.VISIBLE);
                    btn_call_guide.setVisibility(View.VISIBLE);
                    btn_refund.setVisibility(View.VISIBLE);
                } else {
                    btn_call_custom.setVisibility(View.VISIBLE);
                    btn_call_guide.setVisibility(View.VISIBLE);
                }

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

                switch (str.getReviewStatus()) {
                    case Constant.ORDER_EVALUATE_NO:
                        btn_call_guide.setVisibility(View.VISIBLE);
                        btn_evaluate.setVisibility(View.VISIBLE);
                        break;
                    case Constant.ORDER_EVALUATE_YES:
                        btn_delete.setVisibility(View.VISIBLE);
                        btn_call_guide.setVisibility(View.VISIBLE);
                        break;
                }

            break;
            case Constant.ORDER_PAY_REFUND:
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
        showShortToast(msg);
    }
}
