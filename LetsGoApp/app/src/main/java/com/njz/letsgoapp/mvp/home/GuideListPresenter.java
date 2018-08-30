package com.njz.letsgoapp.mvp.home;

import android.content.Context;

import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/8/28
 * Function:
 */

public class GuideListPresenter implements GuideListContract.Presenter {

    Context context;
    GuideListContract.View iView;

    public GuideListPresenter(Context context, GuideListContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void guideSortTop10ByLocation(String location, int type, int limit, int page, Map<String,String> maps) {
        ResponseCallback listener = new ResponseCallback<GuideListModel>() {
            @Override
            public void onSuccess(GuideListModel data) {
                iView.guideSortTop10ByLocationSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.guideSortTop10ByLocationFailed(errorMsg);
            }
        };
        MethodApi.guideSortTop10ByLocation(location, type, limit, page, maps,new OnSuccessAndFaultSub(listener));
    }
}
