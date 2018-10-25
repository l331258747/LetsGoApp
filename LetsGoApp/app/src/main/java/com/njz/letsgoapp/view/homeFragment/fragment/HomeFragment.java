package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
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
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.adapter.home.HomeGuideAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.map.LocationUtil;
import com.njz.letsgoapp.mvp.find.DynamicNiceContract;
import com.njz.letsgoapp.mvp.find.DynamicNicePresenter;
import com.njz.letsgoapp.mvp.home.HomeContract;
import com.njz.letsgoapp.mvp.home.HomePresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.dialog.LoadingDialog;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CalendarEvent;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.calendar.CalendarActivity;
import com.njz.letsgoapp.view.find.DynamicDetailActivity;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.home.GuideListActivity;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;
import com.njz.letsgoapp.view.mine.SpaceActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyView2;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener,HomeContract.View,DynamicNiceContract.View{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView recycler_view_h;
    private DynamicAdapter dynamicAdapter;
    private HomeGuideAdapter guideAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Disposable calDisposable;
    private Disposable desDisposable;

    private LinearLayout ll_destination, ll_start_time, ll_end_time;
    private TextView tv_destination_content, tv_start_time_content, tv_end_time_content, tv_day_time, btn_trip_setting;
    private RelativeLayout rl_guide_title,dynamic_title;

    private ConvenientBanner convenientBanner;

    private NestedScrollView scrollView;
    private LinearLayout linear_bar2;

    private HomePresenter mPresenter;
    private DynamicNicePresenter nicePresenter;

    private List<GuideModel> guideDatas;
    private List<DynamicModel> dynamicDatas;

    private LocationUtil locationUtil;
    private String city;
    private LoadingDialog loadingDialog;

    private boolean isLoad = false;
    private boolean isBannerLoad,isDynamicLoad,isGuideLoad;

    private int nicePosition;

    public EmptyView2 view_empty;

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


        view_empty = $(R.id.view_empty);
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
        dynamic_title = $(R.id.dynamic_title);

        ll_destination.setOnClickListener(this);
        ll_start_time.setOnClickListener(this);
        ll_end_time.setOnClickListener(this);
        btn_trip_setting.setOnClickListener(this);
        rl_guide_title.setOnClickListener(this);
        dynamic_title.setOnClickListener(this);

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
        Intent intent;
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
                intent = new Intent(context,GuideListActivity.class);
                startActivity(intent);
                break;
            case R.id.dynamic_title:
                ((HomeActivity)activity).setTabIndex(1);
                break;
            case R.id.btn_trip_setting:
                intent = new Intent(context,GuideListActivity.class);
                intent.putExtra(GuideListActivity.START_TIME,tv_start_time_content.getText().toString());
                intent.putExtra(GuideListActivity.END_TIME,tv_end_time_content.getText().toString());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initData() {
        city = MySelfInfo.getInstance().getDefaultCity();
        tv_destination_content.setText(city);

        mPresenter = new HomePresenter(context,this);
        nicePresenter = new DynamicNicePresenter(context,this);

        tv_start_time_content.setText(DateUtil.dateToStr(DateUtil.getNowDate()));
        tv_end_time_content.setText(DateUtil.dateToStr(DateUtil.getDate(1)));
        tv_day_time.setText("2天");

        initRecycler();
        initSwipeLayout();
        intRecyclerH();

        //        loadingDialog = new LoadingDialog(context);
//        loadingDialog.showDialog("定位中...");
//
//        locationUtil = new LocationUtil();
//        locationUtil.startLocation(new LocationUtil.LocationListener() {
//            @Override
//            public void getAdress(int code, LocationModel adress) {
//                LogUtil.e("code:" + code + " adress:" + adress);
//                if(code == 0){
//                    city = adress.getCity();
//                    MySelfInfo.getInstance().setDefaultCity(city);
//                }
//                loadingDialog.dismiss();
//                LoadData();
//            }
//        });
        LoadData();
    }

    private void LoadData(){
        tv_destination_content.setText(city);
        isDynamicLoad =false;
        isBannerLoad = false;
        isGuideLoad = false;
        mPresenter.friendFindAll(city,5,Constant.DEFAULT_PAGE);
        mPresenter.orderReviewsSortTop(city);
        mPresenter.bannerFindByType(Constant.BANNER_HOME,0);

        swipeRefreshLayout.setRefreshing(true);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dynamicAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>(),2);
        recyclerView.setAdapter(dynamicAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        dynamicAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, DynamicDetailActivity.class);
                intent.putExtra(DynamicDetailActivity.FRIENDSTERID,dynamicDatas.get(position).getFriendSterId());
                startActivity(intent);
            }

            @Override
            public void onNiceClick(int position) {
                nicePresenter.friendQueryLikes(dynamicDatas.get(position).isLike(),dynamicDatas.get(position).getFriendSterId());
                nicePosition = position;
            }

            @Override
            public void onHeadClick(int position) {
                Intent intent = new Intent(context, SpaceActivity.class);
                intent.putExtra(SpaceActivity.USER_ID, dynamicAdapter.getItem(position).getUserId());
                startActivity(intent);
            }
        });

        dynamicAdapter.setCheckAllListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)activity).setTabIndex(1);
            }
        });
    }

    private void intRecyclerH(){

        guideDatas = new ArrayList<>();
        recycler_view_h = $(R.id.recycler_view_h);
        recycler_view_h.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        guideAdapter = new HomeGuideAdapter(activity, guideDatas);
        recycler_view_h.setAdapter(guideAdapter);
        recycler_view_h.setNestedScrollingEnabled(false);
        guideAdapter.setOnItemClickListener(new HomeGuideAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID,guideDatas.get(position).getId());
                startActivity(intent);
            }
        });
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isLoad) return;
                isLoad = true;

                isBannerLoad = false;
                isDynamicLoad = false;
                isGuideLoad = false;

                mPresenter.friendFindAll(city,5,Constant.DEFAULT_PAGE);
                mPresenter.orderReviewsSortTop(city);
                mPresenter.bannerFindByType(Constant.BANNER_HOME,0);

            }
        });
    }


    //城市选择
    private void cityPick(){
        Intent intent = new Intent(context, MyCityPickActivity.class);
        intent.putExtra(MyCityPickActivity.LOCATION,tv_destination_content.getText().toString());
        startActivity(intent);
        desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
            @Override
            public void accept(CityPickEvent cityPickEvent) throws Exception {
                desDisposable.dispose();
                if(TextUtils.isEmpty(cityPickEvent.getCity()))
                    return;

                tv_destination_content.setText(cityPickEvent.getCity());
                MySelfInfo.getInstance().setDefaultCity(cityPickEvent.getCity());
                city = cityPickEvent.getCity();

                isDynamicLoad =false;
                isBannerLoad = true;
                isGuideLoad = false;

                mPresenter.friendFindAll(city,5,Constant.DEFAULT_PAGE);
                mPresenter.orderReviewsSortTop(city);

                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    //日历选择
    private void calendarPick() {
        Intent intent = new Intent(context, CalendarActivity.class);
        intent.putExtra(CalendarActivity.CALENDAR_ID, 1);
        startActivity(intent);

        calDisposable = RxBus2.getInstance().toObservable(CalendarEvent.class, new Consumer<CalendarEvent>() {
            @Override
            public void accept(CalendarEvent calendarEvent) throws Exception {
                calDisposable.dispose();
                if(TextUtils.isEmpty(calendarEvent.getStartTime())){
                    return;
                }
                tv_start_time_content.setText(calendarEvent.getStartTime());
                tv_end_time_content.setText(calendarEvent.getEndTime());
                tv_day_time.setText(calendarEvent.getDays());
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
        isBannerLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }

        initBanner(models);
    }

    @Override
    public void bannerFindByTypeFailed(String msg) {
        isBannerLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }
        showLongToast(msg);
    }

    @Override
    public void orderReviewsSortTopSuccess(GuideListModel models) {
        isGuideLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }

        guideDatas = models.getList();
        guideAdapter.setData(guideDatas);
    }

    @Override
    public void orderReviewsSortTopFailed(String msg) {
        isGuideLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }
        showLongToast(msg);
    }

    @Override
    public void friendFriendSterTopSuccess(DynamicListModel models) {
        isDynamicLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }

        dynamicDatas = models.getList();
        dynamicAdapter.setData(dynamicDatas);
        dynamicAdapter.notifyDataSetChanged();

        if(dynamicDatas.size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_follow,"这里还是空空哒~");
            view_empty.setEmptyBackground(R.color.white);
        }else{
            view_empty.setVisible(false);
        }

    }

    @Override
    public void friendFriendSterTopFailed(String msg) {
        isDynamicLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }
        showLongToast(msg);
    }

    @Override
    public void friendQueryLikesSuccess(EmptyModel models) {
        dynamicDatas.get(nicePosition).setLike(!dynamicDatas.get(nicePosition).isLike());
        if(dynamicDatas.get(nicePosition).isLike()){
            dynamicDatas.get(nicePosition).setLikeCount(dynamicDatas.get(nicePosition).getLikeCount() + 1);
        }else{
            dynamicDatas.get(nicePosition).setLikeCount(dynamicDatas.get(nicePosition).getLikeCount() - 1);
        }
        dynamicAdapter.setItemData(nicePosition);
    }

    @Override
    public void friendQueryLikesFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationUtil != null)
            locationUtil.stopLocation();
    }
}

