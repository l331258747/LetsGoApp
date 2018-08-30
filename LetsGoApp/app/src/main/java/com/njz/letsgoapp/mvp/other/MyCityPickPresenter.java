package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.mvp.other.MyCityPickContract.Presenter;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class MyCityPickPresenter implements Presenter {


    Context context;
    MyCityPickContract.View iView;

    public MyCityPickPresenter(Context context, MyCityPickContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void regionFindProAndCity() {
        ResponseCallback listener = new ResponseCallback<List<ProvinceModel>>() {
            @Override
            public void onSuccess(List<ProvinceModel> data) {
                iView.regionFindProAndCitySuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.regionFindProAndCityFailed(errorMsg);
            }
        };
        MethodApi.regionFindProAndCity(new OnSuccessAndFaultSub(listener));
    }
}
