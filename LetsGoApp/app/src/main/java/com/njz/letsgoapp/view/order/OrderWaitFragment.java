package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.OrderWaitAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.order.OrderBean;
import com.njz.letsgoapp.bean.order.Suborders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class OrderWaitFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrderWaitAdapter mAdapter;

    public static Fragment newInstance() {
        OrderWaitFragment fragment = new OrderWaitFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void initView() {
        initRecycler();
        initSwipeLayout();
    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderWaitAdapter(activity, getData());
        mAdapter.setOnItemClickListener(new OrderWaitAdapter.OnItemClickListener() {
            @Override
            public void onClick(String orderNo) {
                startActivity(new Intent(context, OrderDetailActivity.class));
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
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


    public List<OrderBean> getData() {
        List<OrderBean> datas = new ArrayList<>();
        OrderBean orderBean = new OrderBean();
        orderBean.setOrderNo("1111111asd");
        orderBean.setOrderEndTime("2018-08-19");
        orderBean.setOrderStartTime("2018-08-23");
        orderBean.setOrderStatus("待付款");
        orderBean.setOrderTotalPrice(1089);

        List<Suborders> suborderses = new ArrayList<>();
        Suborders suborders = new Suborders();
        suborders.setDay(3);
        suborders.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg");
        suborders.setNum(2);
        suborders.setPrice(109);
        suborders.setTitle("那就走订单那就走订单那就走订单");
        suborders.setTotalPrice(3620);
        suborders.setStatus("待付款");
        suborderses.add(suborders);
        suborderses.add(suborders);
        suborderses.add(suborders);
        suborderses.add(suborders);
        suborderses.add(suborders);
        suborderses.add(suborders);

        orderBean.setSuborderses(suborderses);

        datas.add(orderBean);
        datas.add(orderBean);
        datas.add(orderBean);
        datas.add(orderBean);

        return datas;
    }
}
