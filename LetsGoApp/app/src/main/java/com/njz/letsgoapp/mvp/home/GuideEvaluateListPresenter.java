package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/12
 * Function:
 */

public class GuideEvaluateListPresenter implements GuideEvaluateListContract.Presenter {

    Context context;
    GuideEvaluateListContract.View iView;

    public GuideEvaluateListPresenter(Context context, GuideEvaluateListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void orderReviewsFindGuideReviews(int guideId,String value, int limit, int page) {
        ResponseCallback listener = new ResponseCallback<BasePageModel<EvaluateModel2>>() {
            @Override
            public void onSuccess(BasePageModel<EvaluateModel2> data) {
                iView.orderReviewsFindGuideReviewsSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.orderReviewsFindGuideReviewsFailed(errorMsg);
            }
        };
        MethodApi.orderReviewsFindGuideReviews(guideId,value, limit, page, new OnSuccessAndFaultSub(listener,context,false));
    }
}
