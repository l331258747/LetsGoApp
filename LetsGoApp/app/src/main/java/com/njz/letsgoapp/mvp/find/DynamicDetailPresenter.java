package com.njz.letsgoapp.mvp.find;

import android.content.Context;
import android.content.Intent;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.find.DynamicCommentModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;
import com.njz.letsgoapp.view.login.LoginActivity;

/**
 * Created by LGQ
 * Time: 2018/9/7
 * Function:
 */

public class DynamicDetailPresenter implements DynamicDetailContract.Presenter {

    Context context;
    DynamicDetailContract.View iView;

    public DynamicDetailPresenter(Context context, DynamicDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void friendPersonalFriendSter(int friendSterId) {
        ResponseCallback listener = new ResponseCallback<DynamicModel>() {
            @Override
            public void onSuccess(DynamicModel data) {
                iView.friendPersonalFriendSterSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendPersonalFriendSterFailed(errorMsg);
            }
        };
        MethodApi.friendPersonalFriendSter(friendSterId,new OnSuccessAndFaultSub(listener,context));
    }

    @Override
    public void friendDiscuss(int friendSterId, int discussUserId, String discussContent, int toUserId) {
        ResponseCallback listener = new ResponseCallback<DynamicCommentModel>() {
            @Override
            public void onSuccess(DynamicCommentModel data) {
                iView.friendDiscussSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendDiscussFailed(errorMsg);
            }
        };
        MethodApi.friendDiscuss(friendSterId,discussUserId,discussContent,toUserId,new OnSuccessAndFaultSub(listener,context,false));
    }
}
