package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function:
 */

public class OrderDeletePresenter implements OrderDeleteContract.Presenter {

    Context context;
    OrderDeleteContract.View iView;

    public OrderDeletePresenter(Context context, OrderDeleteContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderDeleteOrder(int id, int status) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.orderDeleteOrderSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderDeleteOrderFailed(errorMsg);
            }
        };
        MethodApi.orderDeleteOrder(id, status ,new OnSuccessAndFaultSub(listener, context));
    }
}
