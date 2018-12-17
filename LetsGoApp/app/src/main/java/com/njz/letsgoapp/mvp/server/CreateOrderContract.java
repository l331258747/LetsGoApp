package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.server.SubmitOrderModel;

/**
 * Created by LGQ
 * Time: 2018/12/17
 * Function:
 */

public interface CreateOrderContract {
    interface Presenter {
        void orderCreateOrder(SubmitOrderModel submitOrderModel);

    }

    interface View {
        void orderCreateOrderSuccess(PayModel model);

        void orderCreateOrderFailed(String msg);

    }
}
