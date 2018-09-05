package com.njz.letsgoapp.mvp.find;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class ReleaseDynamicPresenter implements ReleaseDynamicContract.Presenter {

    Context context;
    ReleaseDynamicContract.View iView;

    public ReleaseDynamicPresenter(Context context, ReleaseDynamicContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void sendSter(String content, List<String> files) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.sendSterSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.sendSterFailed(errorMsg);
            }
        };
        MethodApi.sendSter(content, files, new OnSuccessAndFaultSub(listener));
    }
}
