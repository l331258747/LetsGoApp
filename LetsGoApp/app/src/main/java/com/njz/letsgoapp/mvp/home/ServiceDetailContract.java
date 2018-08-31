package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public interface ServiceDetailContract {

    interface Presenter {
        void getGuideService(int serviceId);
        void bannerFindByType(int type, int id);
    }

    interface View {
        void getGuideServiceSuccess(ServiceDetailModel models);
        void getGuideServiceFailed(String msg);

        void bannerFindByTypeSuccess(List<BannerModel> models);
        void bannerFindByTypeFailed(String msg);

    }
}
