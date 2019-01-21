package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2019/1/17
 * Function:
 */

public class LoginByWxVerifyPresenter implements LoginByWxVerifyContract.Presenter{

    Context context;
    LoginByWxVerifyContract.View iView;

    public LoginByWxVerifyPresenter(Context context, LoginByWxVerifyContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void checkOpenId(String platUid, String platCode) {
        ResponseCallback listener = new ResponseCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                iView.checkOpenIdSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.checkOpenIdFailed(errorMsg);
            }
        };
        MethodApi.checkOpenId(platUid, platCode, new OnSuccessAndFaultSub(listener, context,false));
    }
}
