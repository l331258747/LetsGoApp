package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.GuideListAdapter;
import com.njz.letsgoapp.adapter.home.HomeAdapter;
import com.njz.letsgoapp.adapter.home.HomeGuideAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.HomeContract;
import com.njz.letsgoapp.mvp.home.HomePresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.DateUtil;
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

public class HomeFragment extends BaseFragment implements View.OnClickListener,HomeContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView recycler_view_h;

    HomeAdapter mAdapter;

    LinearLayoutManager linearLayoutManager;

    Disposable calDisposable;
    Disposable desDisposable;

    LinearLayout ll_destination, ll_start_time, ll_end_time;
    TextView tv_destination_content, tv_start_time_content, tv_end_time_content, tv_day_time, btn_trip_setting;
    RelativeLayout rl_guide_title;


    ConvenientBanner convenientBanner;

    NestedScrollView scrollView;
    LinearLayout linear_bar2;

    HomePresenter mPresenter;

    List<GuideModel> datas;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            linear_bar2 = $(R.id.ll_bar2);
            //获取到状态栏的高度
            int statusHeight = AppUtils.getStateBar();
            //动态的设置隐藏布局的高度
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linear_bar2.getLayoutParams();
            params.height = statusHeight;
            linear_bar2.setLayoutParams(params);
        }


        ll_destination = $(R.id.ll_destination);
        tv_destination_content = $(R.id.tv_destination_content);
        ll_start_time = $(R.id.ll_start_time);
        ll_end_time = $(R.id.ll_end_time);
        tv_start_time_content = $(R.id.tv_start_time_content);
        tv_end_time_content = $(R.id.tv_end_time_content);
        tv_day_time = $(R.id.tv_day_time);
        btn_trip_setting = $(R.id.btn_trip_setting);
        convenientBanner = $(R.id.convenientBanner);
        scrollView = $(R.id.scrollView);
        rl_guide_title = $(R.id.rl_guide_title);

        ll_destination.setOnClickListener(this);
        ll_start_time.setOnClickListener(this);
        ll_end_time.setOnClickListener(this);
        btn_trip_setting.setOnClickListener(this);
        rl_guide_title.setOnClickListener(this);

        if(linear_bar2 != null){
            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(AppUtils.px2dip(scrollY) > 250){
                        linear_bar2.setVisibility(View.VISIBLE);
                    }else{
                        linear_bar2.setVisibility(View.GONE);
                    }
                }
            });
        }
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
            case R.id.rl_guide_title:
                startActivity(new Intent(context,GuideListActivity.class));
                break;
            case R.id.btn_trip_setting:
                startActivity(new Intent(context, GuideListActivity.class));
                break;
        }
    }

    @Override
    public void initData() {

        //TODO

        mPresenter = new HomePresenter(context,this);
        mPresenter.friendFriendSterTop("长沙",5,Constant.DEFAULT_PAGE);
        mPresenter.orderReviewsSortTop(Constant.DEFAULT_CITY);
        mPresenter.bannerFindByType(Constant.BANNER_HOME,0);

        tv_destination_content.setText(Constant.DEFAULT_CITY);
        tv_start_time_content.setText(DateUtil.dateToStr(DateUtil.getNowDate()));
        tv_end_time_content.setText(DateUtil.dateToStr(DateUtil.getDate(1)));
        tv_day_time.setText("2天");

        initRecycler();
        initSwipeLayout();
        intRecyclerH();

        //item导游事件
        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
//                showShortToast("点击第" + position);
//                startActivity(new Intent(context, GuideDetailActivity.class));
                //TODO 进入动态详情

            }
        });

        mAdapter.setCheckAllListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(context,GuideListActivity.class));//TODO 进入动态列表
            }
        });


    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(activity, new ArrayList<DynamicModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    HomeGuideAdapter mAdapterh;
    private void intRecyclerH(){

        datas = new ArrayList<>();
        recycler_view_h = $(R.id.recycler_view_h);
        recycler_view_h.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mAdapterh = new HomeGuideAdapter(activity, datas);
        recycler_view_h.setAdapter(mAdapterh);
        recycler_view_h.setNestedScrollingEnabled(false);
        mAdapterh.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID,datas.get(position).getGuideId());
                startActivity(intent);
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

                mPresenter.friendFriendSterTop("长沙",5,Constant.DEFAULT_PAGE);
                mPresenter.orderReviewsSortTop(Constant.DEFAULT_CITY);
                mPresenter.bannerFindByType(Constant.BANNER_HOME,0);

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    //城市选择
    private void cityPick(){

        mPresenter.regionFindProAndCity();
    }

    //日历选择
    private void calendarPick() {
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

    //----------banner start
    public void initBanner(List<BannerModel> homeBanners){
        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView(new LocalImageHolderView.BannerListener<BannerModel>() {

                    @Override
                    public void bannerListener(Context context, int position, BannerModel data, ImageView view) {
                        GlideUtil.LoadImage(context, data.getImgUrl(), view);
                    }
                });
            }
        }, homeBanners)
                .setPointViewVisible(true) //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.oval_white_hollow, R.drawable.oval_theme_solid})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）设置为false后手点击后 停止轮播了
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(Constant.BANNER_RUNNING_TIME);
    }
    //----------banner end

    @Override
    public void bannerFindByTypeSuccess(List<BannerModel> models) {
        initBanner(models);
    }

    @Override
    public void bannerFindByTypeFailed(String msg) {
        showLongToast(msg);
    }

    @Override
    public void orderReviewsSortTopSuccess(List<GuideModel> models) {
        datas = models;
        mAdapterh.setData(datas);
    }

    @Override
    public void orderReviewsSortTopFailed(String msg) {
        showLongToast(msg);
    }

    @Override
    public void friendFriendSterTopSuccess(DynamicListModel models) {
        mAdapter.setData(models.getList());
    }

    @Override
    public void friendFriendSterTopFailed(String msg) {
        showLongToast(msg);
    }

    @Override
    public void regionFindProAndCitySuccess(EmptyModel model) {
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

    @Override
    public void regionFindProAndCityFailed(String msg) {
        showLongToast(msg);
    }
}

