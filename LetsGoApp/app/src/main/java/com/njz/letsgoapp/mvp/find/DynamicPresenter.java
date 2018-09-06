package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class DynamicPresenter implements DynamicContract.Presenter {

    Context context;
    DynamicContract.View iView;

    public DynamicPresenter(Context context, DynamicContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendQueryLikes(boolean isNick, int friendSterId) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.friendQueryLikesSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendQueryLikesFailed(errorMsg);
            }
        };
        MethodApi.friendQueryLikes(isNick,friendSterId,new OnSuccessAndFaultSub(listener));
    }
}
