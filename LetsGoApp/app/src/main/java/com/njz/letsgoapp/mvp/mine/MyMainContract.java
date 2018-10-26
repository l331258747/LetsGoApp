package com.njz.letsgoapp.mvp.mine;

import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public interface MyMainContract {

    interface Presenter{
        void userViewZone(int userId);

    }

    interface View{
        void userViewZoneSuccess(LoginInfoModel data);
        void userViewZoneFailed(String msg);
    }
}
