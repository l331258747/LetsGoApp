package com.njz.letsgoapp.mvp.login;

import android.app.Activity;
import android.content.Context;

import com.njz.letsgoapp.bean.BaseResponse;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View iView;
    Context context;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.iView = view;
        this.context = context;
    }

    @Override
    public void login(String mobile, String password) {
        ResponseCallback listener = new ResponseCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                LogUtil.e("orderinfo:" + data);
                iView.loginSeccess(null);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.loginFailed(errorMsg);
            }
        };
        MethodApi.login(mobile, password, new OnSuccessAndFaultSub(listener, context));
    }
}
