package com.njz.letsgoapp.mvp.order;

import android.content.Context;
import android.content.Intent;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.send.SendOrderModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.view.login.LoginActivity;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function:
 */

public class OrderCreatePresenter implements OrderCreateContract.Presenter {

    Context context;
    OrderCreateContract.View iView;

    public OrderCreatePresenter(Context context, OrderCreateContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderCreateOrder(SendOrderModel data) {

        if (!MySelfInfo.getInstance().isLogin()) {//登录状态
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }

        ResponseCallback listener = new ResponseCallback<PayModel>() {
            @Override
            public void onSuccess(PayModel data) {
                iView.orderCreateOrderSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderCreateOrderFailed(errorMsg);
            }
        };
        MethodApi.orderCreateOrder(data, new OnSuccessAndFaultSub(listener, context));
    }
}
