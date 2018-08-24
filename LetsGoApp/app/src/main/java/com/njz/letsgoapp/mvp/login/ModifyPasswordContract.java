package com.njz.letsgoapp.mvp.login;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public interface ModifyPasswordContract {

    interface Presenter{
        void changePwd(String token,String password,String newPassword);
    }

    interface View{
        void changePwdSuccess(EmptyModel str);
        void changePwdFailed(String msg);
    }
}
