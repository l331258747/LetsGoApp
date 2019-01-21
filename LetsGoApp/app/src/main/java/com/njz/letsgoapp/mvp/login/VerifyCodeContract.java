package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.login.LoginModel;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public interface VerifyCodeContract {

    interface Presenter {
        void userSmsSend(String mobile,String type);
    }

    interface View {

        void userSmsSendSuccess(String str);

        void userSmsSendFailed(String msg);
    }
}
