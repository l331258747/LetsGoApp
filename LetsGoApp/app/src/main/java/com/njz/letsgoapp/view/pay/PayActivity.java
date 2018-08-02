package com.njz.letsgoapp.view.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * 支付界面
 * Created by llt on 2017/12/4.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener{

    private static final int SDK_PAY_FLAG = 1;

    private TextView tvPrice;
    private ImageView ivWX;
    private ImageView ivZhifubao;
    private Button btnPay;

    private double price;
    private int orderId;
    private int payIndex = 1;

    private LoadingDialog loadingDialog;

    private IWXAPI api;

    public static void startActivity(Activity activity, int orderId) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("orderId", orderId);
        activity.startActivity(intent);
    }

    private final MyHandler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler {
        WeakReference<PayActivity> mActivity;

        public MyHandler(PayActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PayActivity theActivity = mActivity.get();
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        theActivity.showShortToast("支付成功");
                            theActivity.paySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        theActivity.showShortToast("支付失败");
                        theActivity.payFailed();
                    }
                    break;
                }

            }
        }
    }


    private void paySuccess(){
        startActivity(new Intent(PayActivity.this,PaySuccessActivity.class));
    }

    private void payFailed(){
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

        loadingDialog = new LoadingDialog(this);
        ivWX.setOnClickListener(this);
        ivZhifubao.setOnClickListener(this);
        btnPay.setOnClickListener(this);

        initWXPay();
    }

    private void initWXPay() {
        api = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID);
        api.registerApp(Constant.WEIXIN_APP_ID);

    }

    @Override
    public void initData() {
//        loadingDialog.showNoText();
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
                    payAli();
                }
                break;
        }
    }


    private void payWeiXin() {
//        loadingDialog.showDialog("正在支付中...");
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

    private void payAli() {
//        loadingDialog.showDialog("正在支付中...");

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2("alipay_sdk=alipay-sdk-java-3.0.52.ALL&app_id=2018071760720301&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE-By+Javen%22%2C%22out_trade_no%22%3A%22080214345815331%22%2C%22passback_params%22%3A%22callback+params%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95-By+Javen%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fnajiuzou.uicp.net%3A26641%2Falipay%2Fnotify_url&sign=LD2EJAXvjuUMWI4UU6kI8P5yYU9I6RP5qusT%2FBY6oxihStJlRjKqjEECL85OpyvEGOdTAprnOwSpaB9SfVq3yGL1%2F53teg%2FxPymq91rbsWdcGuCJ%2FWIanjKkqjWnhUwmu8P4r6vGusTQ%2BwQ%2FCZPze3xHZ2THpBMlIzPcarW38Kk0wXmzyRArZPk4H8ydU0mXzqD2V%2BdmKet8S6wC9Sg9tBdzR9iXcTLVF4VBY%2BgjDPlE%2BeI6gVieFoBE6lD5XPhqmpzUYpso7leI2gH%2BPfz15GKPCPjVMp3Y432v8EpnhDZ1eRwneU34o4y2p3Ov6FQuMkLCT4wWiExZQkJ5Hst2Qw%3D%3D&sign_type=RSA2&timestamp=2018-08-02+14%3A34%3A58&version=1.0", true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
