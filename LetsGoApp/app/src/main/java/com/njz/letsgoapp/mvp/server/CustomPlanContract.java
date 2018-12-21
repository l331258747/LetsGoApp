package com.njz.letsgoapp.mvp.server;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.server.CustomPlanModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/21
 * Function:
 */

public interface CustomPlanContract {
    interface Presenter {
        void orderCreateOrderViewPlan(int orderId);

    }

    interface View {
        void orderCreateOrderViewPlanSuccess(List<CustomPlanModel> model);

        void orderCreateOrderViewPlanFailed(String msg);

    }
}
