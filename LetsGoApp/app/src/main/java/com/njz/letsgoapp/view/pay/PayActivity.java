package com.njz.letsgoapp.view.pay;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.pay.PayContract;
import com.njz.letsgoapp.mvp.pay.PayPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 支付界面
 * Created by llt on 2017/12/4.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener,PayContract.View{

    private static final int SDK_PAY_FLAG = 1;

    private TextView tvPrice;
    private ImageView ivWX;
    private ImageView ivZhifubao;
    private Button btnPay;

    private double price;
    private int orderId;
    private int payIndex = 1;

    private IWXAPI api;

    PayContract.Presenter mPresenter;

    public static void startActivity(Activity activity, int orderId) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("orderId", orderId);
        activity.startActivity(intent);
    }

    @Override
    public void getAliOrderInfoSeccess(String orderInfo) {
        mPresenter.getAliPay(orderInfo);
    }

    @Override
    public void getAliOrderInfoFailed(String msg) {
        LogUtil.e(msg);
    }

    @Override
    public void getAliPaySeccess() {
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
        orderId = getIntent().getIntExtra("orderId", 0);

        tvPrice = $(R.id.tv_price_all);
        ivWX = $(R.id.iv_sel_weixin);
        ivZhifubao = $(R.id.iv_sel_zhifubao);
        btnPay = $(R.id.btn_pay);

        ivWX.setOnClickListener(this);
        ivZhifubao.setOnClickListener(this);
        btnPay.setOnClickListener(this);

        initWXPay();
    }



    @Override
    public void initData() {
        mPresenter = new PayPresenter(this,activity);
        mPresenter.registDisposable();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sel_weixin:
                payIndex = 1;
                ivWX.setImageResource(R.mipmap.ic_pay_select);
                ivZhifubao.setImageResource(R.drawable.oval_unselect);
                break;

            case R.id.iv_sel_zhifubao:
                payIndex = 2;
                ivZhifubao.setImageResource(R.mipmap.ic_pay_select);
                ivWX.setImageResource(R.drawable.oval_unselect);
                break;

            case R.id.btn_pay:
                if (payIndex == 1) {
                    payWeiXin();
                } else {
                    mPresenter.getAliOrderInfo();
                }
                break;
        }
    }

    private void initWXPay() {
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        api.registerApp(Constant.WEIXIN_APP_ID);

    }

    private void payWeiXin() {
        wxPay();
    }

    private void wxPay(){
        PayReq req = new PayReq();
        req.appId			= "asdgaegagwga123123";
        req.partnerId		= "heaea2213123";
        req.prepayId		= "data.prepayid";
        req.nonceStr		= "data.noncestr";
        req.timeStamp		= "data.timestamp";
        req.packageValue	= "data.wvpackage";
        req.sign			= "data.sign";
//        req.extData			= "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeDisposable();
    }
}
