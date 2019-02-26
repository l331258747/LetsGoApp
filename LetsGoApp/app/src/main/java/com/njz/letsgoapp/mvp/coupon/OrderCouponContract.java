package com.njz.letsgoapp.mvp.coupon;

import com.njz.letsgoapp.bean.coupon.CouponModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface OrderCouponContract {

    interface Presenter {
        void userCouponChooseCoupon(float totalOrderPrice);
    }

    interface View {
        void userCouponChooseCouponSuccess(List<CouponModel> models);

        void userCouponChooseCouponFailed(String msg);
    }

}
