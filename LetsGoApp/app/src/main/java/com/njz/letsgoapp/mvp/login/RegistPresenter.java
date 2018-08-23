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
 * Time: 2018/8/23
 * Function:
 */

public class RegistPresenter implements RegistContract.Presenter {

    RegistContract.View iView;
    Context context;

    public RegistPresenter(RegistContract.View iView, Context context) {
        this.iView = iView;
        this.context = context;
    }

    @Override
    public void msgCheckRegister(String mobile, String msg,String password) {
        ResponseCallback listener = new ResponseCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                LogUtil.e("orderinfo:" + data);
                iView.msgCheckRegisterSeccess("成功");
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.msgCheckRegisterFailed(errorMsg);
            }
        };
        MethodApi.msgCheckRegister(mobile,msg, password, new OnSuccessAndFaultSub(listener, context));
    }

    @Override
    public void userSmsSend(String mobile, String type) {
        ResponseCallback listener = new ResponseCallback<VerifyModel>() {
            @Override
            public void onSuccess(VerifyModel data) {
                iView.userSmsSendSeccess("成功：" + data);
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
