package com.njz.letsgoapp.mvp.coupon;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface ActivityReceiveSubmitContract {

    interface Presenter {
        void userCouponPublish(int eventId);
    }

    interface View {
        void userCouponPublishSuccess(String models);

        void userCouponPublishFailed(String msg);
    }

}
