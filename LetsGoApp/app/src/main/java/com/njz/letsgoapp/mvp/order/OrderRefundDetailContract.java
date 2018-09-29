package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.order.OrderRefundDetailModel;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public interface OrderRefundDetailContract {

    interface Presenter {
        void orderRefundQueryOrderRefundDetails(int refundId);
    }

    interface View{
        void orderRefundQueryOrderRefundDetailsSuccess(OrderRefundDetailModel str);
        void orderRefundQueryOrderRefundDetailsFailed(String msg);
    }
}
