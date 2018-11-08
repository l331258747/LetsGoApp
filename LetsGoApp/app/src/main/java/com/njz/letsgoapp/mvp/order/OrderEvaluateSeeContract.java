package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public interface OrderEvaluateSeeContract {

    interface Presenter {
        void orderReviewsQueryOrderReview(int orderId);
    }

    interface View {
        void orderReviewsQueryOrderReviewSuccess(EvaluateModel2 str);
        void orderReviewsQueryOrderReviewFailed(String msg);
    }
}
