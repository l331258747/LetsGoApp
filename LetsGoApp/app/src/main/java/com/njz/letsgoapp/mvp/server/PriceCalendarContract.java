package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.server.PriceCalendarModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;

import java.util.List;

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
