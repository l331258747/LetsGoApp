package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.home.EvaluateModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;

/**
 * Created by LGQ
 * Time: 2018/9/12
 * Function:
 */

public interface GuideEvaluateListContract {

    interface Presenter {
        void orderReviewsFindGuideReviews(int guideId, int limit, int page);
    }

    interface View {
        void orderReviewsFindGuideReviewsSuccess(BasePageModel<EvaluateModel> models);
        void orderReviewsFindGuideReviewsFailed(String msg);

    }

}
