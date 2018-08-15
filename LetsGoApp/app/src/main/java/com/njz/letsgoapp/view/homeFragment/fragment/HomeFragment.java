package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.HomeAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.home.GuideListActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView recyclerView;

    HomeAdapter mAdapter;
    HomeData homeData;

    LinearLayoutManager linearLayoutManager;

    Disposable calDisposable;
    Disposable desDisposable;

    LinearLayout ll_destination, ll_start_time, ll_end_time;
    TextView tv_destination_content, tv_start_time_content, tv_end_time_content, tv_day_time, btn_trip_setting;

    ConvenientBanner convenientBanner;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        initRecycler();
        initSwipeLayout();

        ll_destination = $(R.id.ll_destination);
        tv_destination_content = $(R.id.tv_destination_content);
        ll_start_time = $(R.id.ll_start_time);
        ll_end_time = $(R.id.ll_end_time);
        tv_start_time_content = $(R.id.tv_start_time_content);
        tv_end_time_content = $(R.id.tv_end_time_content);
        tv_day_time = $(R.id.tv_day_time);
        btn_trip_setting = $(R.id.btn_trip_setting);
        convenientBanner = $(R.id.convenientBanner);

        ll_destination.setOnClickListener(this);
        ll_start_time.setOnClickListener(this);
        ll_end_time.setOnClickListener(this);
        btn_trip_setting.setOnClickListener(this);


    }

    public void initBanner(List<HomeData.HomeBanner> homeBanners){
        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView(new LocalImageHolderView.BannerListener<HomeData.HomeBanner>() {

                    @Override
                    public void bannerListener(Context context, int position, HomeData.HomeBanner data, ImageView view) {
                        GlideUtil.LoadImage(context, data.getImgUrl(), view);
                    }
                });
            }
        }, homeBanners)
                .setPointViewVisible(true) //设置指示器是否可见
                .startTurning(4000)//设置自动切换（同时设置了切换时间间隔）
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
//                    .setOnItemClickListener(this) //设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_destination:
                cityPick();
                break;
            case R.id.ll_start_time:
                calendarPick();
                break;
            case R.id.ll_end_time:
                calendarPick();
                break;
            case R.id.btn_trip_setting:
                showLongToast("设置行程");
                startActivity(new Intent(context, GuideListActivity.class));
                break;
        }
    }

    private void cityPick(){
        showShortToast("城市选择");
        startActivity(new Intent(context, CityPickActivity.class));
        activity.overridePendingTransition(0, 0);
        desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
            @Override
            public void accept(CityPickEvent cityPickEvent) throws Exception {
                tv_destination_content.setText(cityPickEvent.getCity());
                desDisposable.dispose();
            }
        });
    }

    private void calendarPick() {
        showLongToast("日历选择");
        Intent intent = new Intent(context, CalendarActivity.class);
        intent.putExtra("CalendarTag", 1);
        startActivity(intent);

        calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
            @Override
            public void accept(CalendarEvent calendarEvent) throws Exception {

                tv_start_time_content.setText(calendarEvent.getStartTime());
                tv_end_time_content.setText(calendarEvent.getEndTime());
                tv_day_time.setText(calendarEvent.getDays());
                calDisposable.dispose();
            }
        });
    }


    @Override
    public void initData() {
        //item导游事件
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                showShortToast("点击第" + position);
                startActivity(new Intent(context, GuideDetailActivity.class));
            }
        });

        mAdapter.setCheckAllListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,GuideListActivity.class));
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        homeData = new HomeData(new ArrayList<HomeData.HomeBanner>(), new ArrayList<HomeData.Guide>());

        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(activity, homeData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeData homeData = getHomeData();
                mAdapter.setData(homeData);
                initBanner(homeData.getHomeBanners());
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
        HomeData.Guide guide = new HomeData.Guide(bannerImg, headImg, "那就走", 4, 5615, 2210, 366d, "安静地分行阿我不管看不惯阿嘎哥啊恩格斯噶十多个阿萨德噶尔");
        List<String> serviceTags = new ArrayList<>();
        serviceTags.add("私人定制");
        serviceTags.add("向导陪游");
        guide.setServiceTags(serviceTags);
        guides.add(guide);
        guides.add(guide);
        guides.add(guide);

        return new HomeData(homeBanners, guides);
    }

}

