package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.home.DynamicModel;

/**
 * Created by LGQ
 * Time: 2018/9/7
 * Function:
 */

public interface DynamicDetailContract {

    interface Presenter {
        void friendPersonalFriendSter(int friendSterId);
    }

    interface View {
        void friendPersonalFriendSterSuccess(DynamicModel models);

        void friendPersonalFriendSterFailed(String msg);
    }
}
