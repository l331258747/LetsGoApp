package com.njz.letsgoapp.mvp.pay;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.njz.letsgoapp.bean.AliPay;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.view.pay.PayResult;

import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/3
 * Function:
 */

public class PayPresenter implements PayContract.Presenter {

    Activity activity;
    PayContract.View iView;
    Disposable disposable;

    public PayPresenter(PayContract.View view,Activity activity) {
        this.iView = view;
        this.activity = activity;
    }

    @Override
    public void getAliOrderInfo() {
        ResponseCallback getTopListener = new ResponseCallback<AliPay>() {
            @Override
            public void onSuccess(AliPay t) {
                LogUtil.e("onSuccess");
                String orderinfo = t.getData();
                LogUtil.e("orderinfo:"+orderinfo);
                iView.getAliOrderInfoSeccess(t.getData());
//                payAli(orderinfo);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.getAliOrderInfoFailed(errorMsg);
            }
        };
        MethodApi.appPay(new OnSuccessAndFaultSub(getTopListener,activity));
    }



    @Override
    public void getAliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                RxBus2.getInstance().post(new PayResult(result));
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @Override
    public void registDisposable() {
        disposable = RxBus2.getInstance().toObservable(PayResult.class, new Consumer<PayResult>() {
            @SuppressWarnings("unchecked")
            @Override
            public void accept(PayResult payResult) throws Exception {
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        theActivity.showShortToast("支付成功");
                    iView.getAliPaySeccess();

                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        theActivity.showShortToast("支付失败");
                    iView.getAliPayFailed();
                }
            }
        });
    }

    @Override
    public void closeDisposable() {
        disposable.dispose();
    }

}
