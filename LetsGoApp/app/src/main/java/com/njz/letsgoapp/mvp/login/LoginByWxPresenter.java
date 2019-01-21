package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.other.WXInfoModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class LoginByWxPresenter implements LoginByWxContract.Presenter {

    Context context;
    LoginByWxContract.View iView;

    public LoginByWxPresenter(Context context, LoginByWxContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void loginByWeixin(WXInfoModel model,boolean isShow) {
        ResponseCallback listener = new ResponseCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                iView.loginByWeixinSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.loginByWeixinFailed(errorMsg);
            }
        };
        MethodApi.loginByWeixin(model, new OnSuccessAndFaultSub(listener, context,isShow));
    }
}
