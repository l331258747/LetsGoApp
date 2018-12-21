package com.njz.letsgoapp.mvp.server;

import android.content.Context;

import com.njz.letsgoapp.bean.server.CustomPlanModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function:
 */

public class CustomPlanPresenter implements CustomPlanContract.Presenter{

    Context context;
    CustomPlanContract.View iView;

    public CustomPlanPresenter(Context context, CustomPlanContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderCreateOrderViewPlan(int orderId) {
        ResponseCallback listener = new ResponseCallback<List<CustomPlanModel>>() {
            @Override
            public void onSuccess(List<CustomPlanModel> data) {
                iView.orderCreateOrderViewPlanSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderCreateOrderViewPlanFailed(errorMsg);
            }
        };
        MethodApi.orderCreateOrderViewPlan(orderId,new OnSuccessAndFaultSub(listener, context));
    }
}
