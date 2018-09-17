package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.GuideListAdapter;
import com.njz.letsgoapp.adapter.mine.FansListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.mine.FansListModel;
import com.njz.letsgoapp.bean.mine.FansModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.mine.FansListContract;
import com.njz.letsgoapp.mvp.mine.FansListPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/8/17
 * Function:
 */

public class FansListActivity extends BaseActivity implements FansListContract.View {

    public static final String TITLE = "TITLE";
    public static final String TYPE = "TYPE";
    public static final String USER_ID = "USER_ID";


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout layout_parent;

    FansListAdapter mAdapter;
    String title = "";
    int type;//0我的粉丝，1我的关注
    int userId;

    FansListPresenter mPresenter;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = intent.getStringExtra(TITLE);
        type = intent.getIntExtra(TYPE,0);
        userId = intent.getIntExtra(USER_ID,0);
    }

    @Override
    public void initView() {

        showLeftAndTitle(title);

        initRecycler();
        initSwipeLayout();

        layout_parent = $(R.id.layout_parent);
        layout_parent.setBackgroundColor(getResources().getColor(R.color.parent_background));
    }

    @Override
    public void initData() {
        mPresenter = new FansListPresenter(context,this);
        getRefreshData();
    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FansListAdapter(activity,new ArrayList<FansModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);

        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new FansListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intentSpace = new Intent(context, SpaceActivity.class);
                intentSpace.putExtra(SpaceActivity.USER_ID, mAdapter.getDatas().get(position).getUserId());
                startActivity(intentSpace);
            }
        });

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
        mPresenter.userFindFans(type,userId, Constant.DEFAULT_LIMIT,Constant.DEFAULT_PAGE);
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        mPresenter.userFindFans(type,userId, Constant.DEFAULT_LIMIT,page);
    }


    @Override
    public void userFindFansSuccess(FansListModel data) {
        List<FansModel> datas = data.getList();

        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
        swipeRefreshLayout.setRefreshing(false);

        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }
        loadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void userFindFansFailed(String msg) {
        showShortToast(msg);

        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

}
