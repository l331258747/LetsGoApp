package com.njz.letsgoapp.view.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.order.OrderWaitAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.order.OrderModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.order.OrderListContract;
import com.njz.letsgoapp.mvp.order.OrderListPresenter;
import com.njz.letsgoapp.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class OrderListFragment extends BaseFragment implements OrderListContract.View, View.OnClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrderWaitAdapter mAdapter;
//    private LoadMoreWrapper loadMoreWrapper;

    private int payStatus;
    private boolean isViewCreated;
    private boolean hidden;
    TextView tvLogin;

    private OrderListPresenter mPresenter;

    int page;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    public static Fragment newInstance(int payStatus) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("PAY_STATUS", payStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            payStatus = bundle.getInt("PAY_STATUS");
        }
        isViewCreated = true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initView() {
        tvLogin = $(R.id.tv_login);
        tvLogin.setOnClickListener(this);

        initRecycler();
        initSwipeLayout();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);//比oncreate先执行
        if (isVisibleToUser && isViewCreated && !isLoad) {
            if(setLogin()){
                getRefreshData();
            }
        }
    }

    //本页面登录后刷新，第一次进入刷新，
    @Override
    public void onResume() {
        super.onResume();
        if(hidden) return;
        if(getUserVisibleHint()){
            if(setLogin()){
                getRefreshData();
            }
        }
    }

    //切换底部标签的时候回调用，解决从其他页面登录后（个人中心），数据刷新不了。
    public void setHidden(boolean hidden){
        this.hidden = hidden;
        if(!hidden){
            if(getUserVisibleHint()){
                if(setLogin()){
                    getRefreshData();
                }
            }
        }
    }

    public boolean setLogin(){
        boolean isLogin;
        if (!MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            isLogin = false;
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            isLogin = true;
        }
        return isLogin;
    }

    @Override
    public void initData() {
        mPresenter = new OrderListPresenter(context,this);
    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderWaitAdapter(activity, new ArrayList<OrderModel>());
//        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mAdapter);

        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new OrderWaitAdapter.OnItemClickListener() {
            @Override
            public void onClick(int orderId) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("ORDER_ID",orderId);
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
//                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
//                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getRefreshData();
            }
        });
    }

    private void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        mPresenter.orderQueryOrderList(payStatus);
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        mPresenter.orderQueryOrderList(payStatus);
    }


    @Override
    public void orderQueryOrderListSuccess(List<OrderModel> models) {
        List<OrderModel> datas = models;

        isLoad = false;
//        if (datas.size() >= Constant.DEFAULT_LIMIT) {
//            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
//        } else {
//            // 显示加载到底的提示
//            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
//        }
        swipeRefreshLayout.setRefreshing(false);

        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }
//        loadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void orderQueryOrderListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
//        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login) {
            startActivity(new Intent(context, LoginActivity.class));
        }
    }
}
