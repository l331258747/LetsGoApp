package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.home.BannerModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public interface BannerContract {
    interface Presenter {
        void bannerFindByType(int type, int id);
    }

    interface View {
        void bannerFindByTypeSuccess(List<BannerModel> models);
        void bannerFindByTypeFailed(String msg);

    }
}
