package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class OrderEvaluateSeePresenter implements OrderEvaluateSeeContract.Presenter {

    Context context;
    OrderEvaluateSeeContract.View iView;

    public OrderEvaluateSeePresenter(Context context, OrderEvaluateSeeContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderReviewsQueryOrderReview(int orderId) {
        ResponseCallback listener = new ResponseCallback<EvaluateModel2>() {
            @Override
            public void onSuccess(EvaluateModel2 data) {
                iView.orderReviewsQueryOrderReviewSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderReviewsQueryOrderReviewFailed(errorMsg);
            }
        };
        MethodApi.orderReviewsQueryOrderReview(orderId, new OnSuccessAndFaultSub(listener,context));
    }
}
