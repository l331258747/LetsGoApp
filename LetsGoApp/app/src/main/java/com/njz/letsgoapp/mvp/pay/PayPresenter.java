package com.njz.letsgoapp.mvp.pay;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.EvaluateServicesModel;
import com.njz.letsgoapp.bean.order.AliPay;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.WxPayEvent;
import com.njz.letsgoapp.view.pay.PayResult;
import com.njz.letsgoapp.view.pay.PayResultModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.List;
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
    Disposable disposableWx;


    public PayPresenter(PayContract.View view,Activity activity) {
        this.iView = view;
        this.activity = activity;
    }

    @Override
    public void getAliOrderInfo(String orderId,List<Integer> userCouponIds) {
        LogUtil.e("orderId:" + orderId);
        ResponseCallback getTopListener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String t) {
                LogUtil.e("orderinfo:"+t);
                iView.getAliOrderInfoSuccess(t);
//                payAli(orderinfo);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.getAliOrderInfoFailed(errorMsg);
            }
        };
        MethodApi.orderPayAliPay(orderId,userCouponIds,new OnSuccessAndFaultSub(getTopListener,activity));
    }

    @Override
    public void getWxOrderInfo(String orderId,List<Integer> userCouponIds) {
        ResponseCallback getTopListener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String t) {
                LogUtil.e("onSuccess");
                iView.getWxOrderInfoSuccess(t);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.getWxOrderInfoFailed(errorMsg);
            }
        };
        MethodApi.orderPayWxPay(orderId,userCouponIds,new OnSuccessAndFaultSub(getTopListener,activity));
    }


    @Override
    public void getAliPay(final String orderInfo) {
        if(TextUtils.isEmpty(orderInfo)) {
            ToastUtil.showShortToast(activity,"orderInfo 为空");
            return;
        }
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
    public void getWxPay(String orderInfo,IWXAPI api) {
        if(TextUtils.isEmpty(orderInfo)){
            ToastUtil.showShortToast(activity,"orderInfo 为空");
            return;
        }
        Gson gson = new Gson();
        PayResultModel data = gson.fromJson(orderInfo, PayResultModel.class);

        PayReq req = new PayReq();
        req.appId			= data.appid;
        req.partnerId		= data.partnerid;
        req.prepayId		= data.prepayid;
        req.nonceStr		= data.noncestr;
        req.timeStamp		= data.timestamp;
        req.packageValue	= data.wvpackage;
        req.sign			= data.sign;
//        req.extData			= "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
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
                    iView.getAliPaySuccess();

                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        theActivity.showShortToast("支付失败");
                    iView.getAliPayFailed();
                }
            }
        });

        disposableWx = RxBus2.getInstance().toObservable(WxPayEvent.class, new Consumer<WxPayEvent>() {
            @Override
            public void accept(WxPayEvent wxPayEvent) throws Exception {
                if(wxPayEvent.isSuccess())
                    iView.getWxPaySuccess();
                else
                    iView.getWxPayFailed();
            }
        });
    }

    @Override
    public void closeDisposable() {
        if(disposable!=null && !disposable.isDisposed())
            disposable.dispose();
        if(disposableWx !=null && !disposableWx.isDisposed())
            disposableWx.dispose();
    }

    @Override
    public void orderPayAppQuery(String outTradeNo,String type) {
        ResponseCallback getTopListener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String t) {
                iView.orderPayAppQuerySuccess(t);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderPayAppQueryFailed(errorMsg);
            }
        };
        MethodApi.orderPayAppQuery(outTradeNo,type,new OnSuccessAndFaultSub(getTopListener,activity,false));
    }

}
