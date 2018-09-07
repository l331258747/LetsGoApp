package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

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
        MethodApi.friendPersonalFriendSter(friendSterId,new OnSuccessAndFaultSub(listener));
    }
}
