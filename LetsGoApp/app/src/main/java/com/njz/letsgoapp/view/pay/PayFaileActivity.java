package com.njz.letsgoapp.view.pay;

import android.view.View;
import android.widget.Button;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;


/**
 * 支付失败页面
 * Created by llt on 2017/12/13.
 */

public class PayFaileActivity extends BaseActivity {

    private Button btnRePay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_fail;
    }

    @Override
    public void initView() {
        showLeftAndTitle("支付失败");
        btnRePay = $(R.id.btn_repay);

        btnRePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
