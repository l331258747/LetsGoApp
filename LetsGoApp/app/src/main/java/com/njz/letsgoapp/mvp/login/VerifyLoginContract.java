package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public interface VerifyLoginContract {

    interface Presenter {
        void msgCheckLogin(String mobile,String msg);
        void userSmsSend(String mobile,String type);
    }

    interface View {
        void msgCheckLoginSuccess(LoginModel str);

        void msgCheckLoginFailed(String msg);

        void userSmsSendSuccess(String str);

        void userSmsSendFailed(String msg);
    }
}
