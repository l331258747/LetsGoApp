package com.njz.letsgoapp.view.notify;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.notify.SystemMsgAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.notify.NotifyListContract;
import com.njz.letsgoapp.mvp.notify.NotifyListPresenter;
import com.njz.letsgoapp.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function: 系统消息
 */

public class SystemMsgActivity extends BaseActivity implements NotifyListContract.View{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SystemMsgAdapter mAdapter;

    public LoadMoreWrapper loadMoreWrapper;
    int page = Constant.DEFAULT_PAGE;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    NotifyListPresenter mPresenter;

    public EmptyView view_empty;

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_system;
    }

    @Override
    public void initView() {
        showLeftAndTitle("系统消息");
        initRecycler();
        initSwipeLayout();
        view_empty = $(R.id.view_empty);

    }

    @Override
    public void initData() {
        mPresenter = new NotifyListPresenter(context,this);

        getRefreshData();
    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SystemMsgAdapter(activity, new ArrayList<NotifyMainModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });

    }

    //初始化SwipeLayout
    public void initSwipeLayout() {
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

    public void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getList();
    }

    public void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getList();
    }

    public void getList(){
        mPresenter.msgPushGetReceiveMsgList(Constant.NOTIFY_TYPE_SYSTEM_MSG,Constant.DEFAULT_LIMIT,page);
    }

    @Override
    public void msgPushGetReceiveMsgListSuccess(List<NotifyMainModel> data) {
        List<NotifyMainModel> datas = data;

        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }

        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
        swipeRefreshLayout.setRefreshing(false);

        if(mAdapter.getDatas().size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_order,"矮油，咋还没有消息捏");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void msgPushGetReceiveMsgListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);

        if(msg.startsWith("-")){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyView.BtnClickLisener() {
                @Override
                public void onClick() {
                    getRefreshData();
                }
            });
        }
    }
}
