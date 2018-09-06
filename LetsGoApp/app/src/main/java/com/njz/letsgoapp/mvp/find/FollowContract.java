package com.njz.letsgoapp.mvp.find;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public interface FollowContract {

    interface Presenter {
        void userFocusOff(boolean isNick, int focusId);
    }

    interface View {
        void userFocusOffSuccess(EmptyModel models);
        void userFocusOffFailed(String msg);
    }
}
