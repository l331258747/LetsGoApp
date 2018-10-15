package com.njz.letsgoapp.mvp.pay;

import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by LGQ
 * Time: 2018/8/3
 * Function:
 */

public interface PayContract {

    interface Presenter {
        void getAliOrderInfo(String orderId);

        void getWxOrderInfo(String orderId);

        void getAliPay(String orderInfo);

        void getWxPay(String orderInfo,IWXAPI api);

        void registDisposable();

        void closeDisposable();

    }

    interface View {
        void getAliOrderInfoSuccess(String orderInfo);

        void getAliOrderInfoFailed(String msg);

        void getWxOrderInfoSuccess(String orderInfo);

        void getWxOrderInfoFailed(String msg);

        void getAliPaySuccess();

        void getAliPayFailed();

        void getWxPaySuccess();
    }

}
