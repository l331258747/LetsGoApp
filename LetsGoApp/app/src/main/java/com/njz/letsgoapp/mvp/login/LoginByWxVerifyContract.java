package com.njz.letsgoapp.mvp.login;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public interface LoginByWxVerifyContract {

    interface Presenter {
        void checkOpenId(String platUid, String platCode);
    }

    interface View {
        void checkOpenIdSuccess(Integer loginModel);
        void checkOpenIdFailed(String msg);
    }

}
