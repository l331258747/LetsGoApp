package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.order.OrderRefundModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public interface OrderRefundListContract {

    interface Presenter {
        void orderRefundQueryOrderRefundList(int limit,int page);
    }

    interface View{
        void orderRefundQueryOrderRefundListSuccess(List<OrderRefundModel> data);
        void orderRefundQueryOrderRefundListFailed(String msg);
    }
}
