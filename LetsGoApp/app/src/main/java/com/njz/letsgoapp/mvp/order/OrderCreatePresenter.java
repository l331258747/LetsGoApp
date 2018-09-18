package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.send.SendOrderModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

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
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
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
