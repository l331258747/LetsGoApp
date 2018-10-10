package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.BasePageModel;
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
    public void orderRefundQueryOrderRefundList(int limit,int page) {
        ResponseCallback listener = new ResponseCallback<BasePageModel<OrderRefundModel>>() {
            @Override
            public void onSuccess(BasePageModel<OrderRefundModel> data) {
                iView.orderRefundQueryOrderRefundListSuccess(data.getList());
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundQueryOrderRefundListFailed(errorMsg);
            }
        };
        MethodApi.orderRefundQueryOrderRefundList(limit,page,new OnSuccessAndFaultSub(listener));
    }
}
