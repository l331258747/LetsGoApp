package com.njz.letsgoapp.view.order;

import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.order.OrderCancelContract;
import com.njz.letsgoapp.mvp.order.OrderCancelPresenter;
import com.njz.letsgoapp.util.LoginUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.OrderCancelEvent;
import com.njz.letsgoapp.widget.FixedItemEditViewNoLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function: 大订单取消
 */

public class OrderCancelActivity extends BaseActivity implements View.OnClickListener,OrderCancelContract.View {

    LinearLayout ll_reason;
    TextView tv_reason;
    FixedItemEditViewNoLine view_name,view_phone;
    EditText et_special;
    List<String> reasons;
    Button btn_submit;

    OrderCancelPresenter mPresenter;

    int orderId;
    int isMainly;//1为主订单 0位子订单
    String phone;
    String name;

    @Override
    public void getIntentData() {
        super.getIntentData();
        orderId = intent.getIntExtra("ORDER_ID",0);
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        isMainly = intent.getIntExtra("IS_MAINLY",1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_cancel;
    }

    @Override
    public void initView() {
        showLeftAndTitle("取消订单");

        tv_reason = $(R.id.tv_reason);
        ll_reason = $(R.id.ll_reason);
        view_name = $(R.id.view_name);
        view_name.getEtView().setEnabled(false);
        view_phone = $(R.id.view_phone);
        view_phone.getEtView().setEnabled(false);
        et_special = $(R.id.et_special);
        btn_submit = $(R.id.btn_submit);

        view_phone.setEtInputType(InputType.TYPE_CLASS_NUMBER);

        ll_reason.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void initData() {
        reasons = new ArrayList<>();
        reasons.add("原因1");
        reasons.add("原因2");
        reasons.add("原因3");
        reasons.add("原因4");

        view_name.setEtContent(name);
        view_phone.setEtContent(phone);

        mPresenter = new OrderCancelPresenter(context,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_reason:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(context,
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                tv_reason.setText(reasons.get(options1));
                            }
                        })
                        .build();
                pvOptions.setPicker(reasons, null, null);
                pvOptions.show();
                break;
            case R.id.btn_submit:
                if(TextUtils.isEmpty(tv_reason.getText().toString())){
                    showShortToast("请选择取消原因");
                    return;
                }

                mPresenter.orderTravelDeleteOrder(orderId,isMainly,tv_reason.getText().toString(),et_special.getText().toString());
                break;
        }
    }

    @Override
    public void orderTravelDeleteOrderSuccess(EmptyModel str) {
        showShortToast("取消成功");

        RxBus2.getInstance().post(new OrderCancelEvent(isMainly));
        finish();
    }

    @Override
    public void orderTravelDeleteOrderFailed(String msg) {
        showShortToast(msg);
    }
}
