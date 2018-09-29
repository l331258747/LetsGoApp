package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.order.OrderRefundDetailModel;
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

public class OrderRefundDetailPresenter implements OrderRefundDetailContract.Presenter {

    Context context;
    OrderRefundDetailContract.View iView;

    public OrderRefundDetailPresenter(Context context, OrderRefundDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderRefundQueryOrderRefundDetails(int refundId) {
        ResponseCallback listener = new ResponseCallback<OrderRefundDetailModel>() {
            @Override
            public void onSuccess(OrderRefundDetailModel data) {
                iView.orderRefundQueryOrderRefundDetailsSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundQueryOrderRefundDetailsFailed(errorMsg);
            }
        };
        MethodApi.orderRefundQueryOrderRefundDetails(refundId, new OnSuccessAndFaultSub(listener));
    }
}
