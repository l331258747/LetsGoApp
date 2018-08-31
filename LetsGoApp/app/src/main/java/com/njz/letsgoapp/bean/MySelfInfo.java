package com.njz.letsgoapp.bean;

import android.text.TextUtils;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.util.SPUtils;

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

    public boolean isLogin(){
        if(!TextUtils.isEmpty(getUserToken())){
            return true;
        }
        return false;
    }


    public void setData(LoginModel model) {
        SPUtils.getInstance().putString(SPUtils.SP_USER_TOKEN, model.getToken());
    }

    public String getUserToken() {
        return SPUtils.getInstance().getString(SPUtils.SP_USER_TOKEN);
    }

    public void loginOff() {
        SPUtils.getInstance().logoff();
    }



}
