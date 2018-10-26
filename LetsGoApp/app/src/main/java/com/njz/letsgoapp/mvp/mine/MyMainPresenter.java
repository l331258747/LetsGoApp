package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class MyMainPresenter implements MyMainContract.Presenter {

    Context context;
    MyMainContract.View iView;

    public MyMainPresenter(Context context, MyMainContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userViewZone(int userId) {
        ResponseCallback listener = new ResponseCallback<LoginInfoModel>() {
            @Override
            public void onSuccess(LoginInfoModel data) {
                iView.userViewZoneSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userViewZoneFailed(errorMsg);
            }
        };
        MethodApi.userViewZone(userId, new OnSuccessAndFaultSub(listener, context,false));
    }
}
