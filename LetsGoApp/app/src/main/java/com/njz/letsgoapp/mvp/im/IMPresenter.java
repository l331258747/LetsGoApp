package com.njz.letsgoapp.mvp.im;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class IMPresenter implements IMContract.Presenter {

    Context context;
    IMContract.View iView;

    public IMPresenter(Context context, IMContract.View iView) {
        this.context = context;
        this.iView = iView;
    }


    @Override
    public void saveMessage(String fromId, String toId, String chatType, String msg) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.saveMessageSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.saveMessageFailed(errorMsg);
            }
        };
        MethodApi.saveMessage(fromId,toId,chatType,msg,new OnSuccessAndFaultSub(listener,context,false));
    }
}
