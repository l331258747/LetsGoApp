package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface DynamicDeleteContract {

    interface Presenter {
        void friendDeleteFriendSter(int friendSterId);
    }

    interface View {
        void friendDeleteFriendSterSuccess(EmptyModel models);

        void friendDeleteFriendSterFailed(String msg);
    }

}
