package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.bean.send.SendOrderRefundModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/10
 * Function:
 */

public class OrderRefundPresenter implements OrderRefundContract.Presenter {

    Context context;
    OrderRefundContract.View iView;

    public OrderRefundPresenter(Context context, OrderRefundContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderRefundAliRefund(int id, List<Integer> childIds, String cancelReason, String cancelExplain) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.orderRefundAliRefundSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundAliRefundFailed(errorMsg);
            }
        };

        SendOrderRefundModel model = new SendOrderRefundModel();
        model.setId(id);
        model.setChildIds(childIds);
        model.setRefundContent(cancelExplain);
        model.setRefundReason(cancelReason);
        MethodApi.orderRefundAliRefund(model,new OnSuccessAndFaultSub(listener, context));
    }

    @Override
    public void orderRefundRefundAnalysis(int id, List<Integer> childIds) {
        ResponseCallback listener = new ResponseCallback<OrderRefundModel>() {
            @Override
            public void onSuccess(OrderRefundModel data) {
                iView.orderRefundRefundAnalysisSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundRefundAnalysisFailed(errorMsg);
            }
        };

        SendOrderRefundModel model = new SendOrderRefundModel();
        model.setId(id);
        model.setChildIds(childIds);
        model.setRefundContent(null);
        model.setRefundReason(null);
        MethodApi.orderRefundRefundAnalysis(model,new OnSuccessAndFaultSub(listener, context));
    }
}
