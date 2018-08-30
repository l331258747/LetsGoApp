package com.njz.letsgoapp.mvp.home;

import com.njz.letsgoapp.bean.home.GuideListModel;

import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public interface GuideListContract {

    interface Presenter {
        void guideSortTop10ByLocation(String location, int type, int limit, int page, Map<String,String> maps);
    }

    interface View {
        void guideSortTop10ByLocationSuccess(GuideListModel models);

        void guideSortTop10ByLocationFailed(String msg);
    }
}
