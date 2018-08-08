package com.njz.letsgoapp.view.home.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.HomeAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    Disposable calDisposable;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        mSuspensionBar = $(R.id.suspension_bar);
        mSuspensionBar.setVisibility(View.GONE);

        initRecycler();
        initSwipeLayout();
    }

    @Override
    public void initData() {
        //添加城市选择事件
        mAdapter.setDestinationListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("城市选择");
                startActivity(new Intent(context, CityPickActivity.class));
            }
        });

        //添加日历选择事件
        mAdapter.setCalendarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongToast("日历选择");
                Intent intent = new Intent(context, CalendarActivity.class);
                intent.putExtra("CalendarTag", 1);
                startActivity(intent);

                calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
                    @Override
                    public void accept(CalendarEvent calendarEvent) throws Exception {
                        mAdapter.setCalender(calendarEvent.getStartTime()
                        ,calendarEvent.getEndTime()
                        ,calendarEvent.getDays());
                        calDisposable.dispose();
                    }
                });

            }
        });

        //添加查看全部导游事件
        mAdapter.setGuideAllListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongToast("全部导游");
            }
        });

        //设置行程事件
        mAdapter.setTripSettingListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongToast("设置行程");
            }
        });

        //item导游事件
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                showShortToast("点击第" + position);
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        homeData = new HomeData(new ArrayList<HomeData.HomeBanner>(),new ArrayList<HomeData.Guide>());

        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(activity, homeData);
        recyclerView.setAdapter(mAdapter);

        //导游标题悬浮效果
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setData(getHomeData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    //假数据
    private HomeData getHomeData() {
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

        return new HomeData(homeBanners,guides);
    }
}
