package com.njz.letsgoapp.mvp.coupon;

import com.njz.letsgoapp.bean.coupon.CouponModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/16
 * Function:
 */

public interface CouponContract {

    interface Presenter {
        void userCouponList(int useStatus,int limit,int page);
    }

    interface View {
        void userCouponListSuccess(List<CouponModel> models);

        void userCouponListFailed(String msg);
    }

}
