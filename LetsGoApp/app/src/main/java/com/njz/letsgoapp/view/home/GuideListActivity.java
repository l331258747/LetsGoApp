package com.njz.letsgoapp.view.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.GuideListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.GuideData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideListActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView recyclerView;

    GuideListAdapter mAdapter;
    GuideData guideList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_list;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        initRecycler();
        initSwipeLayout();
    }


    //初始化recyclerview
    private void initRecycler() {
        List<GuideData> guideDatas = new ArrayList<>();

        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GuideListAdapter(activity, guideDatas);
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


    public List<GuideData> getData() {
        List<GuideData> guideDatas = new ArrayList<>();
        GuideData guideData = new GuideData();
        guideData.setComment("" + 400);
        guideData.setContent("aegaegjaklegjalkag");
        guideData.setHeadUrl("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        guideData.setName("导游");
        guideData.setPrice(390);
        List<String> serviceItmes = new ArrayList<>();
        serviceItmes.add("向导陪游");
        serviceItmes.add("包车服务");
        serviceItmes.add("代订门票");
        guideData.setServiceItems(serviceItmes);
        guideData.setServiceNum(300 + "");
        guideData.setSex("男");
        guideData.setStars(5);

        guideDatas.add(guideData);
        guideDatas.add(guideData);
        guideDatas.add(guideData);
        guideDatas.add(guideData);
        guideDatas.add(guideData);
        guideDatas.add(guideData);
        guideDatas.add(guideData);


        return guideDatas;
    }

}
