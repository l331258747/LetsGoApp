package com.njz.letsgoapp.view.home.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.ServerOtherAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.ServerOtherData;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.server.ServerListContract;
import com.njz.letsgoapp.mvp.server.ServerListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerOtherFragment extends BaseFragment implements ServerListContract.View{

    private RecyclerView recyclerView;

    private ServerOtherAdapter mAdapter;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题

    ServerListPresenter serverListPresenter;
    private int guideId;
    private int guideServeId;

    public static Fragment newInstance(int guideId,int guideServeId) {
        ServerOtherFragment fragment = new ServerOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("guideId", guideId);
        bundle.putInt("guideServeId", guideServeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            guideId = bundle.getInt("guideId");
            guideServeId = bundle.getInt("guideServeId");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_other;
    }

    @Override
    public void initView() {
        initRecycler();
    }

    @Override
    public void initData() {
        serverListPresenter = new ServerListPresenter(context,this);
        getRefreshData();
    }



    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ServerOtherAdapter(activity, new ArrayList<PlayModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.setNestedScrollingEnabled(false);

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
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData();
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getData();
    }

    public void getData() {
        serverListPresenter.serveGuideServeOrderList("",Constant.DEFAULT_LIMIT,page, "",0,guideId,guideServeId);
    }

    @Override
    public void serveGuideServeOrderListSuccess(List<PlayModel> datas) {
        mAdapter.setData(datas);
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
    }

    @Override
    public void serveGuideServeOrderListFailed(String msg) {
        showShortToast(msg);
    }
}
