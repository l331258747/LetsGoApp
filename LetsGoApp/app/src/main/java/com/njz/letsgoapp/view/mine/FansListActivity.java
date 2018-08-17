package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.FansListAdapter;
import com.njz.letsgoapp.adapter.GuideListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.mine.FansBean;
import com.njz.letsgoapp.view.home.GuideDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/17
 * Function:
 */

public class FansListActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout layout_parent;

    FansListAdapter mAdapter;
    String title = "";

    @Override
    public int getLayoutId() {
        return R.layout.common_swiperefresh_layout;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();

        title = intent.getStringExtra("FansListActivity_title");
    }

    @Override
    public void initView() {

        showLeftAndTitle(title);

        initRecycler();
        initSwipeLayout();

        layout_parent = $(R.id.layout_parent);
        layout_parent.setBackgroundColor(getResources().getColor(R.color.color_line_33));
    }

    @Override
    public void initData() {

    }


    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FansListAdapter(activity, getData());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(context,GuideDetailActivity.class));
            }
        });

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

    public List<FansBean> getData(){
        List<FansBean> fans = new ArrayList<>();
        FansBean fansBean = new FansBean();
        fansBean.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        fansBean.setName("那就走");

        fans.add(fansBean);
        fans.add(fansBean);
        fans.add(fansBean);
        fans.add(fansBean);
        fans.add(fansBean);
        fans.add(fansBean);
        fans.add(fansBean);

        return fans;
    }
}
