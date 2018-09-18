package com.njz.letsgoapp.view.order;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function: 大订单取消
 */

public class OrderCancelActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_cancel;
    }

    @Override
    public void initView() {
        showLeftAndTitle("取消订单");
    }

    @Override
    public void initData() {

    }
}
