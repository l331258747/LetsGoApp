package com.njz.letsgoapp.mvp.coupon;

import com.njz.letsgoapp.bean.coupon.ActivityPopModel;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface ActivityPopContract {

    interface Presenter {
        void orderPopup();
    }

    interface View {
        void orderPopupSuccess(ActivityPopModel models);

        void orderPopupFailed(String msg);
    }

}
