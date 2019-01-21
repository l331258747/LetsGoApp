package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.util.log.LogUtil;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class VerifyCodePresenter implements VerifyCodeContract.Presenter{

    Context context;
    VerifyCodeContract.View iView;

    public VerifyCodePresenter(Context context, VerifyCodeContract.View iView) {
        this.context = context;
        this.iView = iView;
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
