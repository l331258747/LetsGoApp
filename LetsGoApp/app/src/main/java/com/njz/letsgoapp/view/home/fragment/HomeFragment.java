package com.njz.letsgoapp.view.home.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeFragment extends BaseFragment{

    private SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView recyclerView;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        initRecycler();
        initSwipeLayout();

    }

    @Override
    public void initData() {

    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

//        final List<HomeItem2> data = new ArrayList<HomeItem2>();
//        homeAdapter = new HomeAdapter2(activity, data);
//        homeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return data.get(position).getSpanSize();
//            }
//        });
//        recyclerView.setAdapter(homeAdapter);
//
//        homeAdapter.setOnItemClickListener(this);
//
//        homeAdapter.setOnItemChildClickListener(this);
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.blue));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                lastTimeHelp.isRefresh(true);
//                homeDataRequest();
//            }
//        });
    }


}
