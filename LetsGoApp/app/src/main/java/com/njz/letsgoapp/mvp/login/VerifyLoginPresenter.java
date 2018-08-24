package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public class VerifyLoginPresenter implements VerifyLoginContract.Presenter {

    Context context;
    VerifyLoginContract.View iView;

    public VerifyLoginPresenter(Context context, VerifyLoginContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void msgCheckLogin(String mobile, String msg) {
        ResponseCallback listener = new ResponseCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                iView.msgCheckLoginSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.msgCheckLoginFailed(errorMsg);
            }
        };
        MethodApi.msgCheckLogin(mobile, msg, new OnSuccessAndFaultSub(listener, context));
    }

    @Override
    public void userSmsSend(String mobile, String type) {
        ResponseCallback listener = new ResponseCallback<VerifyModel>() {
            @Override
            public void onSuccess(VerifyModel data) {
                iView.userSmsSendSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.userSmsSendFailed(errorMsg);
            }
        };
        MethodApi.userSmsSend(mobile, type, new OnSuccessAndFaultSub(listener, context));
    }
}
