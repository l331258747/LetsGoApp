package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/5
 * Function:
 */

public class BannerPresenter implements BannerContract.Presenter {


    Context context;
    BannerContract.View iView;

    public BannerPresenter(Context context, BannerContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void bannerFindByType(int type, int id) {
        ResponseCallback listener = new ResponseCallback<List<BannerModel>>() {
            @Override
            public void onSuccess(List<BannerModel> data) {
                iView.bannerFindByTypeSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.bannerFindByTypeFailed(errorMsg);
            }
        };
        MethodApi.bannerFindByType(type, id, new OnSuccessAndFaultSub(listener,context,false));

    }
}
