package com.njz.letsgoapp.view.pay;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;

/**
 * 支付成功页面
 * Created by llt on 2017/12/13.
 */

public class PaySuccessActivity extends BaseActivity implements View.OnClickListener {

    private Button btnDetail;
    private Button btnGo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {
        showLeftAndTitle("支付成功");
        btnDetail = $(R.id.btn_detail);
        btnGo = $(R.id.btn_go);

        btnDetail.setOnClickListener(this);
        btnGo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_detail:
                ActivityCollect.getAppCollect().finishAllNotHome();
               HomeActivity activity = (HomeActivity) ActivityCollect.getAppCollect().findActivity(HomeActivity.class);
                if(activity!=null){
                    activity.setTabIndex(2);
                }
                break;
            case R.id.btn_go:
                ActivityCollect.getAppCollect().finishAllNotHome();
                break;
        }
    }
}
