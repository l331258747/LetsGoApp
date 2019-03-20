package com.njz.letsgoapp.view.pay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.njz.letsgoapp.MyApplication;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.order.Suborders;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.mvp.pay.PayContract;
import com.njz.letsgoapp.mvp.pay.PayPresenter;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.order.OrderDetailActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付界面
 * Created by llt on 2017/12/4.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener,PayContract.View{

    public static final String ORDER_ID = "ORDER_ID";
    public static final String COUPON_ID = "COUPON_ID";

    private static final int SDK_PAY_FLAG = 1;

    private TextView tvPrice,tvTitle,tvTime;
    private ImageView ivWX;
    private ImageView ivZhifubao;
    private TextView btnPay;
    private RelativeLayout rl_sel_zhifubao,rl_sel_weixin;

    private double price;
    private PayModel payModel;
    private List<Integer> couponId;
    private int payIndex = 1;

    private IWXAPI api;

    PayContract.Presenter mPresenter;

    public static void startActivity(Context activity, PayModel orderId, List<Integer> couponId) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        intent.putIntegerArrayListExtra(COUPON_ID, (ArrayList<Integer>) couponId);
        activity.startActivity(intent);
    }

    public static void startActivity(Context activity, PayModel orderId) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        intent.putIntegerArrayListExtra(COUPON_ID, null);
        activity.startActivity(intent);
    }

    @Override
    public void getAliOrderInfoSuccess(String orderInfo) {
        mPresenter.getAliPay(orderInfo);
    }

    @Override
    public void getAliOrderInfoFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void getWxOrderInfoSuccess(String orderInfo) {
        mPresenter.getWxPay(orderInfo,api);
    }

    @Override
    public void getWxOrderInfoFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void orderPayAppQuerySuccess(String model) {
        LogUtil.e("orderPayAppQuerySuccess");
    }

    @Override
    public void orderPayAppQueryFailed(String msg) {
        LogUtil.e("orderPayAppQueryFailed");
        showShortToast(msg);
    }

    @Override
    public void getAliPaySuccess() {
        finish();
        startActivity(new Intent(PayActivity.this,PaySuccessActivity.class));
        mPresenter.orderPayAppQuery(payModel.getOutTradeNo(),"AliPay");
    }

    @Override
    public void getAliPayFailed() {
        startActivity(new Intent(PayActivity.this,PayFaileActivity.class));
        mPresenter.orderPayAppQuery(payModel.getOutTradeNo(),"AliPay");
    }

    @Override
    public void getWxPaySuccess() {
        finish();
        mPresenter.orderPayAppQuery(payModel.getOutTradeNo(),"WxPay");
    }

    @Override
    public void getWxPayFailed() {
        mPresenter.orderPayAppQuery(payModel.getOutTradeNo(),"WxPay");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        showLeftAndTitle("支付");
        showRightTv();
        getRightTv().setText("关闭");
        getRightTv().setTextColor(ContextCompat.getColor(context,R.color.color_99));
        getRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payModel = getIntent().getParcelableExtra(ORDER_ID);
        couponId = getIntent().getIntegerArrayListExtra(COUPON_ID);

        tvPrice = $(R.id.tv_price_all);
        ivWX = $(R.id.iv_sel_weixin);
        ivZhifubao = $(R.id.iv_sel_zhifubao);
        btnPay = $(R.id.btn_pay);
        rl_sel_zhifubao = $(R.id.rl_sel_zhifubao);
        rl_sel_weixin = $(R.id.rl_sel_weixin);
        tvTitle = $(R.id.tv_title);
        tvTime = $(R.id.tv_time);

        btnPay.setOnClickListener(this);
        rl_sel_zhifubao.setOnClickListener(this);
        rl_sel_weixin.setOnClickListener(this);

        initWXPay();
    }

    @Override
    public void onBackPressed() {
        //"您确定要放弃支付吗?"
        DialogUtil.getInstance().getDefaultDialog(context, "您确定要放弃支付吗?", new DialogUtil.DialogCallBack() {
            @Override
            public void exectEvent(DialogInterface alterDialog) {
                alterDialog.dismiss();
                if(payModel.getOrderId() == 0){
                    finish();
                    return;
                }
                ActivityCollect.getAppCollect().finishAllNotHome();
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID",payModel.getOrderId());
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public void initData() {
        mPresenter = new PayPresenter(this,activity);
        mPresenter.registDisposable();

        tvPrice.setText(payModel.getTotalAmount());
        tvTitle.setText(payModel.getSubject());
        tvTime.setText(String.format(getResources().getString(R.string.order_pay_time),payModel.getLastPayTime()));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sel_weixin:
                payIndex = 1;
                ivWX.setImageResource(R.mipmap.ic_select);
                ivZhifubao.setImageResource(R.drawable.oval_unselect);
                break;

            case R.id.rl_sel_zhifubao:
                payIndex = 2;
                ivZhifubao.setImageResource(R.mipmap.ic_select);
                ivWX.setImageResource(R.drawable.oval_unselect);
                break;

            case R.id.btn_pay:
                if (payIndex == 1) {
                    if (!MyApplication.mWxApi.isWXAppInstalled()) {
                        ToastUtil.showShortToast(context, "您还未安装微信客户端");
                        return;
                    }
                    mPresenter.getWxOrderInfo(payModel.getOutTradeNo(),couponId);
                } else {
                    mPresenter.getAliOrderInfo(payModel.getOutTradeNo(),couponId);
                }
                break;
        }
    }

    private void initWXPay() {
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        api.registerApp(Constant.WEIXIN_APP_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeDisposable();
    }
}
