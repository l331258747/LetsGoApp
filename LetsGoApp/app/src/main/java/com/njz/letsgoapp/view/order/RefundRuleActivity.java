package com.njz.letsgoapp.view.order;

import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.order.ServiceRefundRuleModel;
import com.njz.letsgoapp.mvp.order.ServiceRefundRuleContract;
import com.njz.letsgoapp.mvp.order.ServiceRefundRulePresenter;

/**
 * Created by LGQ
 * Time: 2018/10/19
 * Function:
 */

public class RefundRuleActivity extends BaseActivity implements ServiceRefundRuleContract.View{

    TextView tv_refund_rule_50,tv_refund_rule_30;

    ServiceRefundRulePresenter mPresenter;

    int serviceId;

    @Override
    public void getIntentData() {
        super.getIntentData();
        serviceId = intent.getIntExtra("serviceId",0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_rule;
    }

    @Override
    public void initView() {
        showLeftAndTitle("退订规则");
        tv_refund_rule_30 = $(R.id.tv_refund_rule_30);
        tv_refund_rule_50 = $(R.id.tv_refund_rule_50);
    }

    @Override
    public void initData() {
        mPresenter = new ServiceRefundRulePresenter(context,this);
        mPresenter.orderRefundFindRefundRule(serviceId,true);
    }

    @Override
    public void orderRefundFindRefundRuleSuccess(ServiceRefundRuleModel str) {
        if(str == null) return;
        tv_refund_rule_30.setText(String.format(getResources().getString(R.string.refund_rule_30),str.getRenegePriceThree().replace(",","-")));
        tv_refund_rule_50.setText(String.format(getResources().getString(R.string.refund_rule_50),str.getRenegePriceFive().replace(",","-")));
    }

    @Override
    public void orderRefundFindRefundRuleFailed(String msg) {
        showShortToast(msg);
    }
}
