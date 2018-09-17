package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderSubmitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.GuideServiceModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.view.home.GuideContractActivity;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.widget.FixedItemTextView;
import com.njz.letsgoapp.widget.FixedItemEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class OrderSubmitActivity extends BaseActivity implements View.OnClickListener {

    public static final String SERVICEMODEL = "SERVICEMODEL";

    FixedItemEditView login_view_name, login_view_phone;
    FixedItemTextView fixed_view_city;
    RecyclerView recyclerView;
    TextView tv_contract,tv_submit;
    EditText et_special;

    List<GuideServiceModel> serviceModels;

    @Override
    public void getIntentData() {
        super.getIntentData();
        serviceModels = intent.getParcelableArrayListExtra(SERVICEMODEL);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_submit;
    }

    @Override
    public void initView() {

        showLeftAndTitle("确认订单");

        login_view_name = $(R.id.login_view_name);
        login_view_phone = $(R.id.login_view_phone);
        fixed_view_city = $(R.id.fixed_view_city);
        recyclerView = $(R.id.recycler_view);
        tv_contract = $(R.id.tv_contract);
        tv_submit = $(R.id.tv_submit);
        et_special = $(R.id.et_special);

        StringUtils.setHtml(tv_contract, getResources().getString(R.string.guide_service_contract));

        tv_contract.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderSubmitAdapter mAdapter = new OrderSubmitAdapter(context, getData());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setNestedScrollingEnabled(false);

        et_special.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // scrollview 与 edittext 滑动冲突
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }


    @Override
    public void initData() {
        fixed_view_city.setContent("张家界");
        getTotalPrice();
    }


    public List<ServiceItem> getData() {
        List<ServiceItem> serviceItems = new ArrayList<>();
        for (GuideServiceModel model : serviceModels) {
            serviceItems.addAll(model.getServiceItems());
        }
        return serviceItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contract:
                startActivity(new Intent(context,GuideContractActivity.class));
                break;
            case R.id.tv_submit:
                PayActivity.startActivity(activity, 12314341);
                break;
        }
    }

    public void getTotalPrice(){
        float price = 0;
        for (GuideServiceModel model : serviceModels) {
            for (ServiceItem item : model.getServiceItems()){
                if(TextUtils.equals(item.getServiceType(), Constant.SERVICE_TYPE_GUIDE)
                        || TextUtils.equals(item.getServiceType(),Constant.SERVICE_TYPE_CAR)){
                    price  = item.getPrice() * item.getTimeDay() + price;
                }else{
                    price  = item.getPrice() * item.getNumber() * item.getTimeDay() + price;
                }
            }
        }
        tv_submit.setText("立即预定（￥" + price +"）");
    }
}
