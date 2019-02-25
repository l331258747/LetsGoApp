package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.mine.CouponAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.mine.CouponModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.coupon.CouponContract;
import com.njz.letsgoapp.mvp.coupon.CouponPresenter;
import com.njz.letsgoapp.view.home.GuideListActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyClickLisener;
import com.njz.letsgoapp.widget.emptyView.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/21
 * Function:
 */

public class CouponFragment extends BaseFragment implements CouponContract.View {


    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CouponAdapter mAdapter;

    private int type = 1;
    private boolean isViewCreated;
    boolean isLoad = false;

    public EmptyView view_empty;

    CouponPresenter mPresenter;

    public static Fragment newInstance(int type) {
        CouponFragment fragment = new CouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("TYPE");
        }
        isViewCreated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);//比oncreate先执行
        if (isVisibleToUser && isViewCreated && !isLoad) {
            getRefreshData();
        }
    }

    public void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData(type);
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getData(type);
    }

    public void getData(int type) {
        mPresenter.userCouponList(type, Constant.DEFAULT_LIMIT, page);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initView() {
        view_empty = $(R.id.view_empty);

        initRecycler();
        initSwipeLayout();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CouponAdapter(activity, new ArrayList<CouponModel>(), type);
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });

        mAdapter.setOnItemClickListener(new CouponAdapter.OnItemClickListener() {
            @Override
            public void onReplyClick(int... positions) {
                for (int position : positions) {
                    loadMoreWrapper.notifyItemChanged(position);
                }
            }
        });

        mAdapter.setOnUserClickListener(new CouponAdapter.OnUserClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, GuideListActivity.class));
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


    @Override
    public void initData() {
        mPresenter = new CouponPresenter(context, this);

        if (getUserVisibleHint()) {
            getRefreshData();
        }
    }

    @Override
    public void userCouponListSuccess(List<CouponModel> models) {
        List<CouponModel> datas = models;

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

        if (datas.size() == 0) {
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_follow, "空空如也");
        } else {
            view_empty.setVisible(false);
        }

    }

    @Override
    public void userCouponListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);

        if (msg.startsWith("-")) {
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    getRefreshData();
                }
            });
        }
    }

}
