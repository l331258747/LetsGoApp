package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.server.PriceCalendarModel;

/**
 * Created by LGQ
 * Time: 2018/12/12
 * Function:
 */

public interface PriceCalendarContract {
    interface Presenter {
        void serveGetMorePrice(String formatIds, String year, String month,int serveId);

    }

    interface View {
        void serveGetMorePriceSuccess(PriceCalendarModel model);

        void serveGetMorePriceFailed(String msg);

    }

}
