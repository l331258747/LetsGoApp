package com.njz.letsgoapp.view.find;

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
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.find.FindContract;
import com.njz.letsgoapp.mvp.find.FindPresenter;
import com.njz.letsgoapp.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class DynamicFragment extends BaseFragment implements FindContract.View, View.OnClickListener {

    TextView tvLogin;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DynamicAdapter mAdapter;
    private LoadMoreWrapper loadMoreWrapper;

    public static final int DYNAMIC_ALL = 0;
    public static final int DYNAMIC_FOLLOW = 1;
    int dynamicTYpe;

    List<DynamicModel> datas;

    FindPresenter mPresenter;
    int page;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    public static Fragment newInstance(int type) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DYNAMIC_TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dynamicTYpe = bundle.getInt("DYNAMIC_TYPE");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find_dynamic;
    }

    @Override
    public void initView() {
        tvLogin = $(R.id.tv_login);
        tvLogin.setOnClickListener(this);

        initRecycler();
        initSwipeLayout();
        setLogin();

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(swipeRefreshLayout != null && tvLogin != null)
//            setLogin();
//    }

    public void setLogin(){
        if (dynamicTYpe == DYNAMIC_FOLLOW && !MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        mPresenter = new FindPresenter(context, this);

        mPresenter.friendFindAll(Constant.DEFAULT_CITY, Constant.DEFAULT_LIMIT, Constant.DEFAULT_PAGE);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

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


    private void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        mPresenter.friendFindAll(Constant.DEFAULT_CITY, Constant.DEFAULT_LIMIT, page);
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        mPresenter.friendFindAll(Constant.DEFAULT_CITY, Constant.DEFAULT_LIMIT, page);
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

    @Override
    public void friendFindAllSuccess(DynamicListModel models) {
        datas = models.getList();

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
    public void friendFindAllFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login) {
            startActivity(new Intent(context, LoginActivity.class));
        }
    }
}
