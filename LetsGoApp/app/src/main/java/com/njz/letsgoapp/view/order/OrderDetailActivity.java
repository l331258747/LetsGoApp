package com.njz.letsgoapp.view.order;

import android.widget.EditText;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.widget.FixedItemEditView;
import com.njz.letsgoapp.widget.FixedItemTextView;

/**
 * Created by LGQ
 * Time: 2018/9/20
 * Function:
 */

public class OrderDetailActivity extends BaseActivity {

    FixedItemTextView fixed_view_city;
    FixedItemEditView login_view_name,login_view_phone;
    EditText et_special;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("查看订单");

        fixed_view_city = $(R.id.fixed_view_city);
        login_view_name = $(R.id.login_view_name);
        login_view_phone = $(R.id.login_view_phone);
        et_special = $(R.id.et_special);


    }

    @Override
    public void initData() {

    }
}
