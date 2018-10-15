package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public class ServiceDetailPresenter implements ServiceDetailContract.Presenter {

    Context context;
    ServiceDetailContract.View iView;

    public ServiceDetailPresenter(Context context, ServiceDetailContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void getGuideService(int serviceId) {
        ResponseCallback listener = new ResponseCallback<ServiceDetailModel>() {
            @Override
            public void onSuccess(ServiceDetailModel data) {
                iView.getGuideServiceSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.getGuideServiceFailed(errorMsg);
            }
        };
        MethodApi.getGuideService(serviceId, new OnSuccessAndFaultSub(listener,context));
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
