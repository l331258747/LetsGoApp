package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.login.LoginModel;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public interface RegistContract {

    interface Presenter {
        void msgCheckRegister(String mobile, String msg, String password);

        void userSmsSend(String mobile,String type);
    }

    interface View {
        void msgCheckRegisterSeccess(String str);

        void msgCheckRegisterFailed(String msg);

        void userSmsSendSeccess(String str);

        void userSmsSendFailed(String msg);
    }
}
