package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.ServiceListModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/31
 * Function:
 */

public class ServiceListPresenter implements ServiceListContract.Presenter {

    Context context;
    ServiceListContract.View iView;

    public ServiceListPresenter(Context context, ServiceListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }


    @Override
    public void getServiceList(int guideId, int serveType,String location) {
        ResponseCallback listener = new ResponseCallback<List<ServiceListModel>>() {
            @Override
            public void onSuccess(List<ServiceListModel> data) {
                iView.getServiceListSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.getServiceListFailed(errorMsg);
            }
        };
        MethodApi.getServiceList(guideId, serveType,location, new OnSuccessAndFaultSub(listener,context,false));
    }
}
