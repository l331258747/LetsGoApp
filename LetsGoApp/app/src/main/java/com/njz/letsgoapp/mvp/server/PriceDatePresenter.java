package com.njz.letsgoapp.mvp.server;

import android.content.Context;

import com.njz.letsgoapp.bean.server.PriceCalendarModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public class PriceDatePresenter implements PriceDateContract.Presenter {

    Context context;
    PriceDateContract.View iView;

    public PriceDatePresenter(Context context, PriceDateContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void serveGetPrice(String formatIds, String travelDates, int serveId) {
        ResponseCallback listener = new ResponseCallback<PriceCalendarModel>() {
            @Override
            public void onSuccess(PriceCalendarModel data) {
                iView.serveGetPriceSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.serveGetPriceFailed(errorMsg);
            }
        };
        MethodApi.serveGetPrice(formatIds, travelDates, serveId,new OnSuccessAndFaultSub(listener, context));
    }
}
