package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.order.OrderRefundListAdapter;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.order.OrderRefundListContract;
import com.njz.letsgoapp.mvp.order.OrderRefundListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundListFragment extends OrderListFragment implements OrderRefundListContract.View{

    OrderRefundListPresenter refundPresenter;
    OrderRefundListAdapter mAdapter;

    public static Fragment newInstance() {
        OrderRefundListFragment fragment = new OrderRefundListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void initData() {
        refundPresenter = new OrderRefundListPresenter(context,this);
    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderRefundListAdapter(activity, new ArrayList<OrderRefundModel>());
        recyclerView.setAdapter(mAdapter);

        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new OrderRefundListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int orderId) {
                Intent intent = new Intent(context, OrderRefundDetailActivity.class);
                intent.putExtra("ORDER_ID",orderId);
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                getMoreData();
            }
        });
    }

    public void getList(){
        refundPresenter.orderRefundQueryOrderRefundList();
    }



    @Override
    public void orderRefundQueryOrderRefundListSuccess(List<OrderRefundModel> data) {
        List<OrderRefundModel> datas = data;
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }
    }

    @Override
    public void orderRefundQueryOrderRefundListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
    }
}
