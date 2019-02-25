package com.njz.letsgoapp.mvp.coupon;

import android.content.Context;

import com.njz.letsgoapp.bean.mine.CouponModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public class OrderCouponPresenter implements OrderCouponContract.Presenter {

    Context context;
    OrderCouponContract.View iView;

    public OrderCouponPresenter(Context context, OrderCouponContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userCouponChooseCoupon(float totalOrderPrice) {
        ResponseCallback listener = new ResponseCallback<List<CouponModel>>() {
            @Override
            public void onSuccess(List<CouponModel> data) {
                iView.userCouponChooseCouponSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userCouponChooseCouponFailed(errorMsg);
            }
        };
        MethodApi.userCouponChooseCoupon(totalOrderPrice, new OnSuccessAndFaultSub(listener, context, false));
    }
}
