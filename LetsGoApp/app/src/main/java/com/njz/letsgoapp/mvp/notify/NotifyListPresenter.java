package com.njz.letsgoapp.mvp.notify;

import android.content.Context;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class NotifyListPresenter implements NotifyListContract.Presenter {

    Context context;
    NotifyListContract.View iView;

    public NotifyListPresenter(Context context, NotifyListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void msgPushGetReceiveMsgList(String type, int limit, int page) {
        ResponseCallback listener = new ResponseCallback<BasePageModel<NotifyMainModel>>() {
            @Override
            public void onSuccess(BasePageModel<NotifyMainModel> data) {
                iView.msgPushGetReceiveMsgListSuccess(data.getData());
            }

            @Override
            public void onFault(String errorMsg) {
                iView.msgPushGetReceiveMsgListFailed(errorMsg);
            }
        };
        MethodApi.msgPushGetReceiveMsgList(type,limit,page,new OnSuccessAndFaultSub(listener));
    }
}
