package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;

/**
 * Created by LGQ
 * Time: 2018/9/12
 * Function:
 */

public interface GuideEvaluateListContract {

    interface Presenter {
        void orderReviewsFindGuideReviews(int guideId,int serveId,String value, int limit, int page);
    }

    interface View {
        void orderReviewsFindGuideReviewsSuccess(BasePageModel<EvaluateModel2> models);
        void orderReviewsFindGuideReviewsFailed(String msg);

    }

}
