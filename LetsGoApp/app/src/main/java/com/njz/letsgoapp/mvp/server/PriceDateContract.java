package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.server.PriceCalendarModel;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public interface PriceDateContract {
    interface Presenter {
        void serveGetPrice(String formatIds, String travelDates, int serveId);

    }

    interface View {
        void serveGetPriceSuccess(PriceCalendarModel model);

        void serveGetPriceFailed(String msg);

    }
}
