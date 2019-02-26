package com.njz.letsgoapp.mvp.coupon;

import android.content.Context;

import com.njz.letsgoapp.bean.coupon.CouponReceiveModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public class ActivityReceivePresenter implements ActivityReceiveContract.Presenter {

    Context context;
    ActivityReceiveContract.View iView;

    public ActivityReceivePresenter(Context context, ActivityReceiveContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userCouponInfo(int eventId) {
        ResponseCallback listener = new ResponseCallback<CouponReceiveModel>() {
            @Override
            public void onSuccess(CouponReceiveModel data) {
                iView.userCouponInfoSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userCouponInfoFailed(errorMsg);
            }
        };
        MethodApi.userCouponInfo(eventId, new OnSuccessAndFaultSub(listener, context));
    }
}
