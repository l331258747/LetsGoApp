package com.njz.letsgoapp.mvp.order;

import com.njz.letsgoapp.bean.order.ServiceRefundRuleModel;

/**
 * Created by LGQ
 * Time: 2018/10/19
 * Function:
 */

public interface ServiceRefundRuleContract {

    interface Presenter {
        void orderRefundFindRefundRule(int serveId,boolean showDialog);
    }

    interface View{
        void orderRefundFindRefundRuleSuccess(ServiceRefundRuleModel str);
        void orderRefundFindRefundRuleFailed(String msg);
    }
}
