package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.mvp.home.HomeContract.Presenter;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class HomePresenter implements Presenter {

    Context context;
    HomeContract.View iView;

    public HomePresenter(Context context, HomeContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void bannerFindByType(int type, int guideId) {
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
        MethodApi.bannerFindByType(type, guideId, new OnSuccessAndFaultSub(listener, context));
    }
}
