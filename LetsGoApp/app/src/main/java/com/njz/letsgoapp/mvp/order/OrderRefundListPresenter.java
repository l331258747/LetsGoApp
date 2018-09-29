package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.order.OrderModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundListPresenter implements OrderRefundListContract.Presenter {

    Context context;
    OrderRefundListContract.View iView;

    public OrderRefundListPresenter(Context context, OrderRefundListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderRefundQueryOrderRefundList() {
        ResponseCallback listener = new ResponseCallback<List<OrderRefundModel>>() {
            @Override
            public void onSuccess(List<OrderRefundModel> data) {
                iView.orderRefundQueryOrderRefundListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundQueryOrderRefundListFailed(errorMsg);
            }
        };
        MethodApi.orderRefundQueryOrderRefundList(new OnSuccessAndFaultSub(listener));
    }
}
