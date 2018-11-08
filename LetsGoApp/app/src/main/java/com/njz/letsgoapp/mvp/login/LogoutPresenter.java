package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class LogoutPresenter implements LogoutContract.Presenter {

    Context context;
    LogoutContract.View iView;

    public LogoutPresenter(Context context, LogoutContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userLogout() {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.userLogoutSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userLogoutFailed(errorMsg);
            }
        };
        MethodApi.userLogout(new OnSuccessAndFaultSub(listener, context,false));
    }
}
