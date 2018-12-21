package com.njz.letsgoapp.mvp.order;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/11
 * Function:
 */

public class OrderEvaluatePresenter implements OrderEvaluateContract.Presenter {

    Context context;
    OrderEvaluateContract.View iView;

    public OrderEvaluatePresenter(Context context, OrderEvaluateContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void upUserReview(int orderId, int guideId, int serviceAttitude, int serviceQuality,int travelArrange,int carCondition,
                             String userContent, List<String> files) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.upUserReviewSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.upUserReviewFailed(errorMsg);
            }
        };
        MethodApi.upUserReview(orderId, guideId, serviceAttitude, serviceQuality,travelArrange,carCondition,
                 userContent, files, new OnSuccessAndFaultSub(listener,context,false));

    }
}
