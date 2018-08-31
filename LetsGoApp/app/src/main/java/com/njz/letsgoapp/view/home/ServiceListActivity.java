package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.HomeAdapter;
import com.njz.letsgoapp.adapter.home.ServiceListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.home.ServiceListModel;
import com.njz.letsgoapp.mvp.home.ServiceListContract;
import com.njz.letsgoapp.mvp.home.ServiceListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceListActivity extends BaseActivity implements ServiceListContract.View{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    ServiceListAdapter mAdapter;

    ServiceListPresenter mPresenter;

    String title;
    String serviceType;
    int guideId;
    List<ServiceListModel> models;

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra("ServiceDetailActivity_title");
        serviceType = intent.getStringExtra("serviceType");
        guideId = intent.getIntExtra("guideId",0);
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
        mAdapter = new ServiceListAdapter(activity, new ArrayList<ServiceListModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                intent = new Intent(context, ServiceDetailActivity.class);
                intent.putExtra("ServiceDetailActivity_title",title);
                intent.putExtra("serviceId",models.get(position).getId());

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
                mPresenter.getServiceList(guideId,serviceType);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter = new ServiceListPresenter(context,this);
        mPresenter.getServiceList(guideId,serviceType);
    }


    @Override
    public void getServiceListSuccess(List<ServiceListModel> models) {
        this.models = models;
        mAdapter.setData(models);
    }

    @Override
    public void getServiceListFailed(String msg) {
        showShortToast(msg);
    }
}
