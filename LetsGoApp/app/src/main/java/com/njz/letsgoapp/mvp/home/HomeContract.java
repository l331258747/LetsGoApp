package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public interface HomeContract {

    interface Presenter {
        void bannerFindByType(int type, int guideId);
    }

    interface View {
        void bannerFindByTypeSuccess(List<BannerModel> models);

        void bannerFindByTypeFailed(String msg);
    }
}
