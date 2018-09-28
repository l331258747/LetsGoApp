package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.order.OrderDetailModel;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public interface OrderDetailContract {

    interface Presenter {
        void orderQueryOrder(int orderId);
    }

    interface View{
        void orderQueryOrderSuccess(OrderDetailModel str);
        void orderQueryOrderFailed(String msg);
    }
}
