package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.other.WXInfoModel;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public interface LoginByWxContract {

    interface Presenter {
        void loginByWeixin(WXInfoModel model,boolean isShow);
    }

    interface View {
        void loginByWeixinSuccess(LoginModel model);
        void loginByWeixinFailed(String msg);
    }
}
