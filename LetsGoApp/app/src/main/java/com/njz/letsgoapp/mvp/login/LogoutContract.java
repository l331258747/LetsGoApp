package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public interface LogoutContract {

    interface Presenter {
        void userLogout();

    }

    interface View {
        void userLogoutSuccess(EmptyModel str);

        void userLogoutFailed(String msg);


    }

}
