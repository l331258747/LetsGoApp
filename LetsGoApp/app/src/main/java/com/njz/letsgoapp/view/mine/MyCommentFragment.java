package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.HomeAdapter;
import com.njz.letsgoapp.adapter.ServiceListAdapter;
import com.njz.letsgoapp.adapter.mine.MyCommentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.mine.MyCommentBean;
import com.njz.letsgoapp.view.home.ServiceDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyCommentAdapter mAdapter;

    public static Fragment newInstance() {
        MyCommentFragment fragment = new MyCommentFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void initView() {


        initRecycler();
        initSwipeLayout();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MyCommentAdapter(activity, getData());
        recyclerView.setAdapter(mAdapter);
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setData(getData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {

    }

    public List<MyCommentBean> getData() {
        List<MyCommentBean> datas = new ArrayList<>();
        MyCommentBean data = new MyCommentBean();
        data.setHeadUrl("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        data.setBodyUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg");
        data.setName("那就走");
        data.setTime("5小时前");
        data.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        data.setBodyContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        return datas;
    }
}
