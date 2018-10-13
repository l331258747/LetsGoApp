package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.ServiceListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.home.ServiceListModel;
import com.njz.letsgoapp.mvp.home.ServiceListContract;
import com.njz.letsgoapp.mvp.home.ServiceListPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.ServiceDetailCloseEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceListActivity extends BaseActivity implements ServiceListContract.View{

    public static final String TITLE = "TITLE";
    public static final String SERVICE_TYPE = "SERVICE_TYPE";
    public static final String GUIDE_ID = "GUIDE_ID";
    public static final String SERVICEITEMS = "SERVICEITEMS";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    ServiceListAdapter mAdapter;

    ServiceListPresenter mPresenter;

    String title;
    int serveType;
    int guideId;
    List<ServiceItem> serviceItems;

    List<ServiceListModel> models;
    Disposable disposable;

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra(TITLE);
        serveType = intent.getIntExtra(SERVICE_TYPE,0);
        guideId = intent.getIntExtra(GUIDE_ID,0);
        serviceItems = intent.getParcelableArrayListExtra(SERVICEITEMS);

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
        mAdapter = new ServiceListAdapter(activity, new ArrayList<ServiceListModel>(),serviceItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        mAdapter.setOnItemClickListener(new ServiceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(context, ServiceDetailActivity.class);
                intent.putExtra(ServiceDetailActivity.TITLE,title);
                intent.putExtra(ServiceDetailActivity.SERVICEID,models.get(position).getId());
                intent.putParcelableArrayListExtra(ServiceDetailActivity.SERVICEITEMS, (ArrayList<ServiceItem>) serviceItems);
                startActivity(intent);
            }

            @Override
            public void onBtnClick(int position) {
                ServiceItem data = new ServiceItem();
                data.setServiceType(models.get(position).getServiceType());
                data.setServeType(models.get(position).getServeType());
                data.setValue(models.get(position).getValue());
                data.setId(models.get(position).getId());
                data.setTitile(models.get(position).getTitle());
                data.setPrice(models.get(position).getServePrice());
                RxBus2.getInstance().post(data);
                finish();
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
                mPresenter.getServiceList(guideId,serveType,MySelfInfo.getInstance().getDefaultCity());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter = new ServiceListPresenter(context,this);
        mPresenter.getServiceList(guideId,serveType, MySelfInfo.getInstance().getDefaultCity());

        disposable = RxBus2.getInstance().toObservable(ServiceDetailCloseEvent.class, new Consumer<ServiceDetailCloseEvent>() {
            @Override
            public void accept(ServiceDetailCloseEvent serviceDetailCloseEvent) throws Exception {
                finish();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
