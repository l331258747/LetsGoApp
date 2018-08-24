package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.login.LoginModel;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public interface LoginContract {

    interface Presenter {
        void login(String mobile, String password);
    }

    interface View {
        void loginSuccess(LoginModel loginModel);
        void loginFailed(String msg);
    }

}
