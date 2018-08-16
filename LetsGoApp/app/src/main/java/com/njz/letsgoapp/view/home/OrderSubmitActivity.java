package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.MainActivity;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.OrderSubmitAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.view.pay.PayActivity;
import com.njz.letsgoapp.widget.FixedItemView;
import com.njz.letsgoapp.widget.LoginItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class OrderSubmitActivity extends BaseActivity implements View.OnClickListener {

    LoginItemView login_view_name, login_view_phone;
    FixedItemView fixed_view_city, fixed_view_calendar, fixed_view_people_num, fixed_view_special;
    RecyclerView recyclerView;
    TextView tv_contract,tv_submit;

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
        fixed_view_calendar = $(R.id.fixed_view_calendar);
        fixed_view_people_num = $(R.id.fixed_view_people_num);
        fixed_view_special = $(R.id.fixed_view_special);
        recyclerView = $(R.id.recycler_view);
        tv_contract = $(R.id.tv_contract);
        tv_submit = $(R.id.tv_submit);

        StringUtils.setHtml(tv_contract, getResources().getString(R.string.guide_service_contract));

        tv_contract.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderSubmitAdapter mAdapter = new OrderSubmitAdapter(context, getData());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void initData() {
        fixed_view_city.setContent("张家界");
        fixed_view_calendar.setContent("2018-07-12 至 2018-07-15 （4天）");
        fixed_view_people_num.setContent("4人");
        fixed_view_special.setContent("特殊要求");
    }


    public List<ServiceItem> getData() {
        List<ServiceItem> serviceItems = new ArrayList<>();
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setContent("咖喱块手机噶隆盛科技噶历史课按理说大家告诉");
        serviceItem.setPrice(360);
        serviceItem.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg");
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
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
}
