package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.BasePageModel;
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
    public void orderQueryOrderList(int payStatus,int limit,int page) {
        ResponseCallback listener = new ResponseCallback<BasePageModel<OrderModel>>() {
            @Override
            public void onSuccess(BasePageModel<OrderModel> data) {
                iView.orderQueryOrderListSuccess(data.getList());
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderQueryOrderListFailed(errorMsg);
            }
        };
        MethodApi.orderQueryOrderList(payStatus,limit,page ,new OnSuccessAndFaultSub(listener));
    }
}
