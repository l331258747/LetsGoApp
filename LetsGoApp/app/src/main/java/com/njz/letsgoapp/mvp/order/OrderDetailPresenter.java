package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public class OrderDetailPresenter implements OrderDetailContract.Presenter {

    Context context;
    OrderDetailContract.View iView;

    public OrderDetailPresenter(Context context, OrderDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderQueryOrder(int orderId) {
        ResponseCallback listener = new ResponseCallback<OrderDetailModel>() {
            @Override
            public void onSuccess(OrderDetailModel data) {
                iView.orderQueryOrderSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderQueryOrderFailed(errorMsg);
            }
        };
        MethodApi.orderQueryOrder(orderId, new OnSuccessAndFaultSub(listener, context));
    }
}
