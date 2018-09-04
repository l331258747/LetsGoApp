package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.home.DynamicListModel;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public interface FindContract {

    interface Presenter {
        void friendFindAll(String location,int limit,int page);
    }

    interface View {
        void friendFindAllSuccess(DynamicListModel models);
        void friendFindAllFailed(String msg);
    }
}
