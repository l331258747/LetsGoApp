package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.send.SendOrderModel;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function:
 */

public interface OrderCreateContract {

    interface Presenter {
        void orderCreateOrder(SendOrderModel data);
    }

    interface View{
        void orderCreateOrderSuccess(PayModel str);
        void orderCreateOrderFailed(String msg);
    }
}
