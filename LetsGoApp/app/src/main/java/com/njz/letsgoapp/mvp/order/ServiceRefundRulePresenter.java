package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.ServiceRefundRuleModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/19
 * Function:
 */

public class ServiceRefundRulePresenter implements ServiceRefundRuleContract.Presenter {

    Context context;
    ServiceRefundRuleContract.View iView;

    public ServiceRefundRulePresenter(Context context, ServiceRefundRuleContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderRefundFindRefundRule(int serveId,boolean showDialog) {
        ResponseCallback listener = new ResponseCallback<ServiceRefundRuleModel>() {
            @Override
            public void onSuccess(ServiceRefundRuleModel data) {
                iView.orderRefundFindRefundRuleSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderRefundFindRefundRuleFailed(errorMsg);
            }
        };

        MethodApi.orderRefundFindRefundRule(serveId,new OnSuccessAndFaultSub(listener, context,showDialog));
    }
}
