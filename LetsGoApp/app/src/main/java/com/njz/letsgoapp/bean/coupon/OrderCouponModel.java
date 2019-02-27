package com.njz.letsgoapp.bean.coupon;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/21
 * Function:
 */

public class OrderCouponModel {

    int count;

    List<CouponModel> userCouponVo;

    public int getCount() {
        return count;
    }

    public List<CouponModel> getUserCouponVo() {
        return userCouponVo;
    }
}
