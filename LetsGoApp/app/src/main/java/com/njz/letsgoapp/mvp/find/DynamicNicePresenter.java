package com.njz.letsgoapp.mvp.find;

import android.content.Context;
import android.content.Intent;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.view.login.LoginActivity;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class DynamicNicePresenter implements DynamicNiceContract.Presenter {

    Context context;
    DynamicNiceContract.View iView;

    public DynamicNicePresenter(Context context, DynamicNiceContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendQueryLikes(boolean isNice, int friendSterId) {
        if (!MySelfInfo.getInstance().isLogin()) {
            context.startActivity(new Intent(context,LoginActivity.class));
            return;
        }

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
        MethodApi.friendQueryLikes(isNice,friendSterId,new OnSuccessAndFaultSub(listener));
    }
}
