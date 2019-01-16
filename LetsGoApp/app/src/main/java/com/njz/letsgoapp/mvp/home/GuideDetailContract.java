package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/29
 * Function:
 */

public interface GuideDetailContract {

    interface Presenter {
//        void bannerFindByType(int type, int id);
        void guideFindGuideDetails(String location, int guideId);
    }

    interface View {
//        void bannerFindByTypeSuccess(List<BannerModel> models);
//        void bannerFindByTypeFailed(String msg);

        void guideFindGuideDetailsSuccess(GuideDetailModel models);
        void guideFindGuideDetailsFailed(String msg);
    }

}
