package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public class DynamicDeletePresenter implements DynamicDeleteContract.Presenter {

    Context context;
    DynamicDeleteContract.View iView;

    public DynamicDeletePresenter(Context context, DynamicDeleteContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendDeleteFriendSter(int friendSterId) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.friendDeleteFriendSterSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendDeleteFriendSterFailed(errorMsg);
            }
        };
        MethodApi.friendDeleteFriendSter(friendSterId, new OnSuccessAndFaultSub(listener, context));
    }
}
