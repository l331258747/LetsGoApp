package com.njz.letsgoapp.mvp.coupon;

import android.content.Context;

import com.njz.letsgoapp.bean.coupon.ActivityPopModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public class ActivityPopPresenter implements ActivityPopContract.Presenter {

    Context context;
    ActivityPopContract.View iView;

    public ActivityPopPresenter(Context context, ActivityPopContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderPopup() {
        ResponseCallback listener = new ResponseCallback<ActivityPopModel>() {
            @Override
            public void onSuccess(ActivityPopModel data) {
                iView.orderPopupSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderPopupFailed(errorMsg);
            }
        };
        MethodApi.orderPopup(new OnSuccessAndFaultSub(listener, context, false));
    }
}
