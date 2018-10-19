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

public class ForgetPresenter implements ForgetContract.Presenter {


    Context context;
    ForgetContract.View iView;

    public ForgetPresenter(Context context, ForgetContract.View view) {
        this.context = context;
        this.iView = view;
    }

    @Override
    public void msgCheckFindBy(String mobile, String msg, String password) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.msgCheckFindBySuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.msgCheckFindByFailed(errorMsg);
            }
        };
        MethodApi.msgCheckFindBy(mobile,msg, password, new OnSuccessAndFaultSub(listener, context));
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
