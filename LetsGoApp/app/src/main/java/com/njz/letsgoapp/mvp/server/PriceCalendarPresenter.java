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

public class PriceCalendarPresenter implements PriceCalendarContract.Presenter {

    Context context;
    PriceCalendarContract.View iView;

    public PriceCalendarPresenter(Context context, PriceCalendarContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void serveGetMorePrice(String formatIds, String year, String month,int serveId) {
        ResponseCallback listener = new ResponseCallback<PriceCalendarModel>() {
            @Override
            public void onSuccess(PriceCalendarModel data) {
                iView.serveGetMorePriceSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.serveGetMorePriceFailed(errorMsg);
            }
        };
        MethodApi.serveGetMorePrice(formatIds, year, month, serveId,new OnSuccessAndFaultSub(listener, context));
    }
}
