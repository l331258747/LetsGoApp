package com.njz.letsgoapp.mvp.mine;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.LabelModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/5
 * Function:
 */

public class LabelPresenter implements LabelContract.Presenter{

    Context context;
    LabelContract.View iView;

    public LabelPresenter(Context context, LabelContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void userLabels() {
        ResponseCallback listener = new ResponseCallback<List<LabelModel>>() {
            @Override
            public void onSuccess(List<LabelModel> data) {
                iView.userLabelsSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.userLabelsFailed(errorMsg);
            }
        };
        MethodApi.userLabels(new OnSuccessAndFaultSub(listener, context));
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
        MethodApi.userChangePersonalData(maps, new OnSuccessAndFaultSub(listener, context));
    }
}
