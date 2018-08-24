package com.njz.letsgoapp.mvp.login;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/8/24
 * Function:
 */

public class ModifyPasswordPresenter implements ModifyPasswordContract.Presenter {

    Context context;
    ModifyPasswordContract.View iView;

    public ModifyPasswordPresenter(Context context, ModifyPasswordContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void changePwd(String token, String password,String newPassword) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.changePwdSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.changePwdFailed(errorMsg);
            }
        };
        MethodApi.changePwd(token, password,newPassword, new OnSuccessAndFaultSub(listener, context));
    }
}
