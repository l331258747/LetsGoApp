package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/10
 * Function:
 */

public interface OrderRefundContract {

    interface Presenter {
        void orderRefundAliRefund(int id, List<Integer> childIds, String cancelReason, String cancelExplain);
        void orderRefundRefundAnalysis(int id, List<Integer> childIds);
    }

    interface View{
        void orderRefundAliRefundSuccess(EmptyModel str);
        void orderRefundAliRefundFailed(String msg);

        void orderRefundRefundAnalysisSuccess(OrderRefundModel str);
        void orderRefundRefundAnalysisFailed(String msg);
    }

}
