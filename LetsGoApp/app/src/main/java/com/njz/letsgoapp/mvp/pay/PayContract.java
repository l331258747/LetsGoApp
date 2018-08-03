package com.njz.letsgoapp.mvp.pay;

import android.widget.TextView;

/**
 * Created by LGQ
 * Time: 2018/8/3
 * Function:
 */

public interface PayContract {

    interface Presenter {
        void getAliOrderInfo();

        void getAliPay(String orderInfo);

        void registDisposable();

        void closeDisposable();

    }

    interface View {
        void getAliOrderInfoSeccess(String orderInfo);

        void getAliOrderInfoFailed(String msg);

        void getAliPaySeccess();

        void getAliPayFailed();

    }

}
