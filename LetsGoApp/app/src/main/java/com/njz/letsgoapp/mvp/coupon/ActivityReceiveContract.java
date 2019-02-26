package com.njz.letsgoapp.mvp.coupon;

import com.njz.letsgoapp.bean.coupon.CouponReceiveModel;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface ActivityReceiveContract {

    interface Presenter {
        void userCouponInfo(int eventId);
    }

    interface View {
        void userCouponInfoSuccess(CouponReceiveModel models);

        void userCouponInfoFailed(String msg);
    }

}
