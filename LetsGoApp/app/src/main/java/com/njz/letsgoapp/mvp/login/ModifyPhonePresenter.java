package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
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

public class ModifyPhonePresenter implements ModifyPhoneContract.Presenter {

    Context context;
    ModifyPhoneContract.View iView;

    public ModifyPhonePresenter(Context context, ModifyPhoneContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void updateMobile(String token, String mobile, String msg, String password) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.updateMobileSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.updateMobileFailed(errorMsg);
            }
        };
        MethodApi.updateMobile(token, mobile, msg, password, new OnSuccessAndFaultSub(listener, context));
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
        MethodApi.userSmsSend(mobile, type, new OnSuccessAndFaultSub(listener, context));
    }
}
