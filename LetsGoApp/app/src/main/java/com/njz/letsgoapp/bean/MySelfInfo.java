package com.njz.letsgoapp.bean;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.util.PreferencesUtils;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public class MySelfInfo {

    private String userToken;

    private MySelfInfo() {
    }

    private final static class HolderClass {
        private final static MySelfInfo INSTANCE = new MySelfInfo();
    }

    public static MySelfInfo getInstance() {
        return HolderClass.INSTANCE;
    }


    public void setData(LoginModel model) {
        PreferencesUtils.getInstance().putString(PreferencesUtils.SP_USER_TOKEN, model.getToken());
    }

    public String getUserToken() {
        return PreferencesUtils.getInstance().getString(PreferencesUtils.SP_USER_TOKEN);
    }

    public void loginOff() {
        PreferencesUtils.getInstance().logoff();
    }



}
