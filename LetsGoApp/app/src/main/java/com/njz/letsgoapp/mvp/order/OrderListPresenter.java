package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.order.OrderModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/25
 * Function:
 */

public class OrderListPresenter implements OrderListContract.Presenter {

    Context context;
    OrderListContract.View iView;

    public OrderListPresenter(Context context, OrderListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderQueryOrderList(int payStatus) {
        ResponseCallback listener = new ResponseCallback<List<OrderModel>>() {
            @Override
            public void onSuccess(List<OrderModel> data) {
                iView.orderQueryOrderListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderQueryOrderListFailed(errorMsg);
            }
        };
        MethodApi.orderQueryOrderList(payStatus, new OnSuccessAndFaultSub(listener));
    }
}
