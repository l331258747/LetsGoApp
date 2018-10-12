package com.njz.letsgoapp.view.notify;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.notify.InteractionMsgAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.notify.NotifyListContract;
import com.njz.letsgoapp.mvp.notify.NotifyListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/11
 * Function: 旅友互动
 */

public class InteractionMsgActivity extends BaseActivity implements NotifyListContract.View {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private InteractionMsgAdapter mAdapter;

    public LoadMoreWrapper loadMoreWrapper;
    int page = Constant.DEFAULT_PAGE;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    NotifyListPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void initView() {
        showLeftAndTitle("旅友互动");
        initRecycler();
        initSwipeLayout();

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
        mAdapter = new InteractionMsgAdapter(activity, new ArrayList<NotifyMainModel>());
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
        mPresenter.msgPushGetReceiveMsgList(Constant.NOTIFY_TYPE_INTERACTION,Constant.DEFAULT_LIMIT,page);
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
    }

    @Override
    public void msgPushGetReceiveMsgListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
