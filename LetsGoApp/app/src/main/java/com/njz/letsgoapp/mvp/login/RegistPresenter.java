package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.util.AESOperator;
import com.njz.letsgoapp.util.ToastUtil;
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
                iView.msgCheckRegisterSuccess("成功");
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
        ResponseCallback listener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                iView.userSmsSendSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                LogUtil.e("onFault" + errorMsg);
                iView.userSmsSendFailed(errorMsg);
            }
        };
        String enString = null;
        try {
            enString = AESOperator.getInstance().encrypt(mobile);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShortToast(context,"加密错误");
            return;
        }
        MethodApi.userSmsSend(enString, type, new OnSuccessAndFaultSub(listener, context));
    }
}
