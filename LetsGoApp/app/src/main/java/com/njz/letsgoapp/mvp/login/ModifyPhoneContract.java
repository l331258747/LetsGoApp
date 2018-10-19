package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.login.VerifyModel;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public interface ModifyPhoneContract {

    interface Presenter {
        void updateMobile(String token, String mobile, String msg, String password);

        void userSmsSend(String mobile,String type);
    }

    interface View {
        void updateMobileSuccess(EmptyModel str);

        void updateMobileFailed(String msg);

        void userSmsSendSuccess(String str);

        void userSmsSendFailed(String msg);
    }
}
