package com.njz.letsgoapp.wxapi;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.WxPayEvent;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, View.OnClickListener {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    private RelativeLayout layoutSuccess;
    private Button btnDetail;
    private Button btnGo;
    private RelativeLayout layoutFailed;
    private Button btnRePay;


    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<WXPayEntryActivity> mActivity;

        public MyHandler(WXPayEntryActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WXPayEntryActivity theActivity = mActivity.get();
            switch (msg.what) {
                case 1:
                    theActivity.showResult(msg.arg2,msg.arg1);
                    break;

            }
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);

        layoutSuccess = $(R.id.layout_pay_success);
        layoutFailed = $(R.id.layout_pay_failed);
        btnDetail = $(R.id.btn_detail);
        btnGo = $(R.id.btn_go);
        btnRePay = $(R.id.btn_repay);

        btnDetail.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        btnRePay.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e("onPayFinish, errCode = " + resp.errCode);

        mHandler.sendMessage(mHandler.obtainMessage(1,resp.errCode,resp.getType()));

    }

    private void showResult(int type,int errCode){
        if (errCode == 0) {
            layoutSuccess.setVisibility(View.VISIBLE);
            layoutFailed.setVisibility(View.GONE);
            RxBus2.getInstance().post(new WxPayEvent(true));
        } else {

            if (type == ConstantsAPI.COMMAND_PAY_BY_WX) {
//                DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
//                builder.setChange("提示");
//                builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(errCode)));
//                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.show();
            }

            layoutSuccess.setVisibility(View.GONE);
            layoutFailed.setVisibility(View.VISIBLE);
            RxBus2.getInstance().post(new WxPayEvent(false));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_detail:
                ActivityCollect.getAppCollect().finishAllNotHome();
                HomeActivity activity = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
                if(activity!=null){
                    activity.setTabIndex(2);
                }
                break;

            case R.id.btn_go:
                ActivityCollect.getAppCollect().finishAllNotHome();
                HomeActivity activity2 = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
                activity2.setTabIndex(2);
                activity2.getOrderFragment().setIndex(1);
                break;

            case R.id.btn_repay:
                finish();
                break;
        }
    }
}