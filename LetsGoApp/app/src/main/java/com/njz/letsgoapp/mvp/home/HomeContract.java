package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.home.NoticeItem;
import com.njz.letsgoapp.bean.other.ProvinceModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public interface HomeContract {

    interface Presenter {
        void bannerFindByType(int type, int guideId);
        void orderReviewsSortTop(String location);
        void friendFindAll(String location, int limit, int page);
        void orderCarouselOrder();
    }

    interface View {
        void bannerFindByTypeSuccess(List<BannerModel> models);
        void bannerFindByTypeFailed(String msg);

        void orderReviewsSortTopSuccess(GuideListModel models);
        void orderReviewsSortTopFailed(String msg);

        void friendFriendSterTopSuccess(DynamicListModel models);
        void friendFriendSterTopFailed(String msg);

        void orderCarouselOrderSuccess(List<NoticeItem> models);
        void orderCarouselOrderFailed(String msg);
    }
}
