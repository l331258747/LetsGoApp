package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.other.ProvinceModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public interface MyCityPickContract {

    interface Presenter {
        void regionFindProAndCity();
    }

    interface View {
        void regionFindProAndCitySuccess(List<ProvinceModel> models);
        void regionFindProAndCityFailed(String msg);
    }
}
