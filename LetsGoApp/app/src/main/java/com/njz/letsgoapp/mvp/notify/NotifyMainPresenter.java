package com.njz.letsgoapp.mvp.notify;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function:
 */

public class NotifyMainPresenter implements NotifyMainContract.Presenter {

    Context context;
    NotifyMainContract.View iView;

    public NotifyMainPresenter(Context context, NotifyMainContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void msgPushGetSendMsgList() {
        ResponseCallback listener = new ResponseCallback<List<EmptyModel>>() {
            @Override
            public void onSuccess(List<EmptyModel> data) {
                iView.msgPushGetSendMsgListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.msgPushGetSendMsgListFailed(errorMsg);
            }
        };
        MethodApi.msgPushGetSendMsgList(new OnSuccessAndFaultSub(listener));
    }
}
