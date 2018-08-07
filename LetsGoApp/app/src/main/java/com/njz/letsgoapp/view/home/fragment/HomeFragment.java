package com.njz.letsgoapp.view.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.adapter.HomeAdapter;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView recyclerView;

    HomeAdapter mAdapter;
    HomeData homeData;

    RelativeLayout mSuspensionBar;
    LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        initDataTest();

        initRecycler();
        initSwipeLayout();

    }

    private void initDataTest() {
        String bannerImg = "http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg";
        String headImg = "http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg";
        List<HomeData.HomeBanner> homeBanners = new ArrayList<>();
        HomeData.HomeBanner homeBanner = new HomeData.HomeBanner(bannerImg);
        homeBanners.add(homeBanner);
        homeBanners.add(homeBanner);
        homeBanners.add(homeBanner);

        List<HomeData.Guide> guides = new ArrayList<>();
        HomeData.Guide guide = new HomeData.Guide(bannerImg, headImg, "那就走", 5, 5615, 2210, 366d, "安静地分行阿我不管看不惯阿嘎哥啊恩格斯噶十多个阿萨德噶尔");
        guides.add(guide);
        guides.add(guide);
        guides.add(guide);

        homeData = new HomeData(homeBanners,guides);
    }

    @Override
    public void initData() {
        mAdapter.setDestinationListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CityPickActivity.class));
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        mSuspensionBar = $(R.id.suspension_bar);
        mSuspensionBar.setVisibility(View.GONE);

        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new HomeAdapter(activity, homeData.getHomeBanners(),homeData.getGuides());

        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到列表第二个可见的View
                View view = linearLayoutManager.findViewByPosition(2);
                //判断View的top值是否小于悬浮条的高度
                if (view == null || view.getTop()<0) {
                    //被顶掉的效果
                    mSuspensionBar.setVisibility(View.VISIBLE);
                } else {
                    mSuspensionBar.setVisibility(View.GONE);
                }
            }
        });

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
