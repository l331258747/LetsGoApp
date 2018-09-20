package com.njz.letsgoapp.view.pay;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.pay.PayContract;
import com.njz.letsgoapp.mvp.pay.PayPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * 支付界面
 * Created by llt on 2017/12/4.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener,PayContract.View{

    public static final String ORDER_ID = "ORDER_ID";

    private static final int SDK_PAY_FLAG = 1;

    private TextView tvPrice,tvTitle,tvTime;
    private ImageView ivWX;
    private ImageView ivZhifubao;
    private Button btnPay;
    private RelativeLayout rl_sel_zhifubao,rl_sel_weixin;

    private double price;
    private PayModel payModel;
    private int payIndex = 1;

    private IWXAPI api;

    PayContract.Presenter mPresenter;

    public static void startActivity(Activity activity, PayModel orderId) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    @Override
    public void getAliOrderInfoSuccess(String orderInfo) {
        mPresenter.getAliPay(orderInfo);
    }

    @Override
    public void getAliOrderInfoFailed(String msg) {
        LogUtil.e(msg);
    }

    @Override
    public void getWxOrderInfoSuccess(String orderInfo) {
        mPresenter.getWxPay(orderInfo,api);
    }

    @Override
    public void getWxOrderInfoFailed(String msg) {
        LogUtil.e(msg);
    }

    @Override
    public void getAliPaySuccess() {
        startActivity(new Intent(PayActivity.this,PaySuccessActivity.class));
    }

    @Override
    public void getAliPayFailed() {
        startActivity(new Intent(PayActivity.this,PayFaileActivity.class));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        showLeftAndTitle("支付");
        payModel = getIntent().getParcelableExtra(ORDER_ID);

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
                    mPresenter.getWxOrderInfo();
                } else {
                    mPresenter.getAliOrderInfo(payModel.getOutTradeNo());
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
