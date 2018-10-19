package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;

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
        void msgCheckRegisterSuccess(String str);

        void msgCheckRegisterFailed(String msg);

        void userSmsSendSuccess(String str);

        void userSmsSendFailed(String msg);
    }
}
