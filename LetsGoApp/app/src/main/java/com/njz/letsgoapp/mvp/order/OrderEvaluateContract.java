package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/11
 * Function:
 */

public interface OrderEvaluateContract {
    interface Presenter {
        void upUserReview(int orderId,
                          int guideId,
                          int guideService,
                          int carCondition,
                          int buyService,
                          int travelArrange,
                          String userContent,
                          List<String> files);
    }

    interface View {
        void upUserReviewSuccess(EmptyModel str);
        void upUserReviewFailed(String msg);
    }
}
