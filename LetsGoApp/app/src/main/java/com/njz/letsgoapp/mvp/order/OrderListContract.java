package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.OrderModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/25
 * Function:
 */

public interface OrderListContract {

    interface Presenter {
        void orderQueryOrderList(int payStatus);
    }

    interface View{
        void orderQueryOrderListSuccess(List<OrderModel> data);
        void orderQueryOrderListFailed(String msg);
    }
}
