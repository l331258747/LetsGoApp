package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.mvp.find.FollowContract.Presenter;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class FollowPresenter implements Presenter {

    Context context;
    FollowContract.View iView;

    public FollowPresenter(Context context, FollowContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userFocusOff(boolean isNick, int focusId) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.userFocusOffSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userFocusOffFailed(errorMsg);
            }
        };
        MethodApi.userFocusOff(isNick,focusId,new OnSuccessAndFaultSub(listener));
    }
}
