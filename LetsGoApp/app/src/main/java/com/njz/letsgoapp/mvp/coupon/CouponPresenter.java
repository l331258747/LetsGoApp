package com.njz.letsgoapp.mvp.coupon;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
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

public class CouponPresenter implements CouponContract.Presenter {

    Context context;
    CouponContract.View iView;

    public CouponPresenter(Context context, CouponContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userCouponList(int useStatus,int limit,int page) {
        ResponseCallback listener = new ResponseCallback<List<CouponModel>>() {
            @Override
            public void onSuccess(List<CouponModel> data) {
                iView.userCouponListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userCouponListFailed(errorMsg);
            }
        };
        String useStatusStr = null;
        if(useStatus != -1)
            useStatusStr = useStatus + "";
        MethodApi.userCouponList(useStatusStr,limit,page,new OnSuccessAndFaultSub(listener, context,false));
    }
}
