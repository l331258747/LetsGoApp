package com.njz.letsgoapp.mvp.server;

import android.content.Context;

import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.SubmitOrderModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/12/17
 * Function:
 */

public class CreateOrderPresenter implements CreateOrderContract.Presenter{

    Context context;
    CreateOrderContract.View iView;

    public CreateOrderPresenter(Context context, CreateOrderContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderCreateOrder(SubmitOrderModel submitOrderModel) {
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
        MethodApi.orderCreateOrder(submitOrderModel,new OnSuccessAndFaultSub(listener, context));
    }
}
