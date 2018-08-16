package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.HomeAdapter;
import com.njz.letsgoapp.adapter.ServiceListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.ServiceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceListActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    ServiceListAdapter mAdapter;


    String title;

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra("ServiceDetailActivity_title");
        if(TextUtils.isEmpty(title)){
            title = "";
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void initView() {

        showLeftAndTitle(title + "列表");

        initRecycler();
        initSwipeLayout();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ServiceListAdapter(activity, getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                intent = new Intent(context, ServiceDetailActivity.class);
                intent.putExtra("ServiceDetailActivity_title",title);
                startActivity(intent);
            }
        });
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setData(getData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {

    }


    public List<ServiceItem> getData(){
        List<ServiceItem> serviceItems = new ArrayList<>();

        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setContent("咖喱块手机噶隆盛科技噶历史课按理说大家告诉");
        serviceItem.setPrice(360);
        serviceItem.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg");

        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);
        serviceItems.add(serviceItem);

        return serviceItems;
    }
}
