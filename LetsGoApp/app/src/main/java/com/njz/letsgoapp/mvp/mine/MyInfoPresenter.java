package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.io.File;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class MyInfoPresenter implements MyInfoContract.Presenter {

    Context context;
    MyInfoContract.View iView;

    public MyInfoPresenter(Context context, MyInfoContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userChangePersonalData(MyInfoData maps) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.userChangePersonalDataSuccess("成功");
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userChangePersonalDataFailed(errorMsg);
            }
        };
        MethodApi.userChangePersonalData(maps, new OnSuccessAndFaultSub(listener,context));
    }

    @Override
    public void upUpload(File file) {
        ResponseCallback listener = new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                iView.upUploadSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.upUploadFailed(errorMsg);
            }
        };
        MethodApi.upUpload(file, new OnSuccessAndFaultSub(listener));
    }
}
