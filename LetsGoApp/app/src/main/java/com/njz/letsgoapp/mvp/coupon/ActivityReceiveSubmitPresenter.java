package com.njz.letsgoapp.mvp.coupon;

import android.content.Context;

import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public class ActivityReceiveSubmitPresenter implements ActivityReceiveSubmitContract.Presenter {

    Context context;
    ActivityReceiveSubmitContract.View iView;

    public ActivityReceiveSubmitPresenter(Context context, ActivityReceiveSubmitContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userCouponPublish(int eventId) {
        ResponseCallback listener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                iView.userCouponPublishSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userCouponPublishFailed(errorMsg);
            }
        };
        MethodApi.userCouponPublish(eventId, new OnSuccessAndFaultSub(listener, context));
    }
}
