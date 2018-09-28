package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public interface OrderCancelContract {

    interface Presenter {
        void orderTravelDeleteOrder(int orderId);
    }

    interface View{
        void orderTravelDeleteOrderSuccess(EmptyModel str);
        void orderTravelDeleteOrderFailed(String msg);
    }
}
