package com.njz.letsgoapp.view.home.serverFragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.ServerOtherAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.ServerOtherData;
import com.njz.letsgoapp.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerOtherFragment extends BaseFragment {

    private RecyclerView recyclerView;

    private ServerOtherAdapter mAdapter;

    private LoadMoreWrapper loadMoreWrapper;
    private int page;
    private int isLoadType = 1;//1下拉刷新，2上拉加载
    private boolean isLoad = false;//是否在加载，重复加载问题

    public static Fragment newInstance() {
        ServerOtherFragment fragment = new ServerOtherFragment();
        return fragment;
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
        getRefreshData();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ServerOtherAdapter(activity, new ArrayList<ServerOtherData>());
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
        List<ServerOtherData> datas = new ArrayList<>();
        ServerOtherData item = new ServerOtherData();
        item.setImg(Constant.TEST_IMG_URL);
        item.setPrice(4.5f);
        item.setCount(400);
        item.setLocation("长沙");
        item.setTitle("那就走那就走那就走那就走那就走");
        item.setServerName("私人定制");

        ServerOtherData item2 = new ServerOtherData();
        item2.setImg(Constant.TEST_IMG_URL);
        item2.setPrice(4.5f);
        item2.setCount(400);
        item2.setLocation("长沙");
        item2.setTitle("那就走");
        item2.setServerName("私人定制");

        for (int i = 0; i < 7; i++) {
            if(i%3==0){
                datas.add(item);
            }else{
                datas.add(item2);
            }
        }

        mAdapter.setData(datas);
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
    }
}
