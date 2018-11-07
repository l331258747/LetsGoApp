package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class SpacePresenter implements SpaceContract.Presenter {

    Context context;
    SpaceContract.View iView;

    public SpacePresenter(Context context, SpaceContract.View iView) {
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
        MethodApi.userViewZone(userId, new OnSuccessAndFaultSub(listener, context));
    }

    @Override
    public void friendPersonalFriendSter(int userId, int limit, int page) {
        ResponseCallback listener = new ResponseCallback<DynamicListModel>() {
            @Override
            public void onSuccess(DynamicListModel data) {
                iView.friendPersonalFriendSterSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.friendPersonalFriendSterFailed(errorMsg);
            }
        };
        MethodApi.friendPersonalFriendSter(userId, limit,page,new OnSuccessAndFaultSub(listener, context,false));
    }
}
