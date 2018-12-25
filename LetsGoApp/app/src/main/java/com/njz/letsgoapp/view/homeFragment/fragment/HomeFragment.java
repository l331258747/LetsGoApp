package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.HomeGuideAdapter;
import com.njz.letsgoapp.adapter.home.HomeServerAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.home.NoticeItem;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.HomeContract;
import com.njz.letsgoapp.mvp.home.HomePresenter;
import com.njz.letsgoapp.mvp.server.ServerListContract;
import com.njz.letsgoapp.mvp.server.ServerListPresenter;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.home.GuideDetailActivity;
import com.njz.letsgoapp.view.home.GuideListActivity;
import com.njz.letsgoapp.view.other.ServerSearchActivity;
import com.njz.letsgoapp.view.other.WebViewActivity;
import com.njz.letsgoapp.view.server.ServerListActivity;
import com.njz.letsgoapp.view.server.ServiceDetailActivity2;
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

public class HomeFragment extends BaseFragment implements View.OnClickListener,HomeContract.View,ServerListContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView recycler_view_h;
    private HomeGuideAdapter guideAdapter;
    private HomeServerAdapter playAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout notice_ll;
    private ViewFlipper view_flipper;
    private TextView tv_grid_guide,tv_grid_car,tv_grid_feature,tv_grid_custom,tv_grid_hotel,tv_grid_ticket;

    private Disposable desDisposable;

    private RelativeLayout rl_guide_title;

    private ConvenientBanner convenientBanner;

    private HomePresenter mPresenter;
    private ServerListPresenter serverListPresenter;

    private List<GuideModel> guideDatas;

    private String city;

    private boolean isLoad = false;
    private boolean isBannerLoad,isDynamicLoad,isGuideLoad;

    public EmptyView2 view_empty,view_empty_guide;

    private TextView tv_city_pick,tv_search;
    private boolean isflipper;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        tv_search = $(R.id.tv_search);
        tv_grid_guide = $(R.id.tv_grid_guide);
        tv_grid_car = $(R.id.tv_grid_car);
        tv_grid_feature = $(R.id.tv_grid_feature);
        tv_grid_custom = $(R.id.tv_grid_custom);
        tv_grid_hotel = $(R.id.tv_grid_hotel);
        tv_grid_ticket = $(R.id.tv_grid_ticket);
        tv_grid_guide.setOnClickListener(this);
        tv_grid_car.setOnClickListener(this);
        tv_grid_feature.setOnClickListener(this);
        tv_grid_custom.setOnClickListener(this);
        tv_grid_hotel.setOnClickListener(this);
        tv_grid_ticket.setOnClickListener(this);

        tv_city_pick = $(R.id.tv_city_pick);
        view_empty = $(R.id.view_empty);
        view_empty_guide = $(R.id.view_empty_guide);
        convenientBanner = $(R.id.convenientBanner);
        rl_guide_title = $(R.id.rl_guide_title);

        rl_guide_title.setOnClickListener(this);
        tv_city_pick.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        initTextBanner();

    }

    //-----srart 最新预订
    private ArrayList<NoticeItem> noticeItems = new ArrayList<>();

    public void initTextBanner(){
        notice_ll = $(R.id.notice_ll);
        view_flipper = $(R.id.view_flipper);
        view_flipper.setAutoStart(false);
        view_flipper.setFlipInterval(Constant.BANNER_RUNNING_TIME); // ms

        notice_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNotice();
            }
        });
    }

    private void initTextBannerData(List<NoticeItem> strs){
        noticeItems = new ArrayList<>();
        noticeItems.addAll(strs);
    }

    private void goNotice() {
        if(noticeItems.size() == 0) return;
        Intent intent = new Intent(context, GuideDetailActivity.class);
        intent.putExtra(GuideDetailActivity.GUIDEID, noticeItems.get(view_flipper.getDisplayedChild()).getGuideId());
        startActivity(intent);
    }

    /**
     * 公告
     */
    public void addNotice() {
        isflipper = false;
        int size = noticeItems.size();
        view_flipper.removeAllViews();

        if(size == 0){
            view_flipper.stopFlipping();
            return;
        }

        if(size == 1){
            View view = View.inflate(context, R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.textview1)).setText(noticeItems.get(0).getMsg());
            view_flipper.addView(view);
            view_flipper.stopFlipping();
            return;
        }

        view_flipper.startFlipping();
        isflipper = true;
        for (int i = 0; i < size; i++) {
            View view = View.inflate(context, R.layout.view_flipper_item_layout, null);
            ((TextView) view.findViewById(R.id.textview1)).setText(noticeItems.get(i).getMsg());
            view_flipper.addView(view);
        }
    }

    //-----end 最新预订


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_grid_ticket:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_TICKET_ID);
                startActivity(intent);
                break;
            case R.id.tv_grid_guide:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_GUIDE_ID);
                startActivity(intent);
                break;
            case R.id.tv_grid_car:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_CAR_ID);
                startActivity(intent);
                break;
            case R.id.tv_grid_custom:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_CUSTOM_ID);
                startActivity(intent);
                break;
            case R.id.tv_grid_feature:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_FEATURE_ID);
                startActivity(intent);
                break;
            case R.id.tv_grid_hotel:
                intent = new Intent(context,ServerListActivity.class);
                intent.putExtra("SERVER_ID",Constant.SERVER_TYPE_HOTEL_ID);
                startActivity(intent);
                break;
            case R.id.tv_city_pick:
                cityPick();
                break;
            case R.id.tv_search:
                intent = new Intent(context, ServerSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_guide_title:
                intent = new Intent(context,GuideListActivity.class);
                startActivity(intent);

        }
    }

    @Override
    public void initData() {
        city = MySelfInfo.getInstance().getDefaultCity();
        tv_city_pick.setText(city);

        mPresenter = new HomePresenter(context,this);
        serverListPresenter = new ServerListPresenter(context,this);

        initRecycler();
        initSwipeLayout();
        intRecyclerH();

        LoadData();
    }

    private void LoadData(){
        isDynamicLoad =false;
        isBannerLoad = false;
        isGuideLoad = false;
        serverListPresenter.serveGuideServeOrderList(0,5,1,city,1,0,0);
        mPresenter.orderReviewsSortTop(city);
        mPresenter.bannerFindByType(Constant.BANNER_HOME,0);
        mPresenter.orderCarouselOrder();

        swipeRefreshLayout.setRefreshing(true);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        playAdapter = new HomeServerAdapter(activity, new ArrayList<ServerDetailModel>());
        recyclerView.setAdapter(playAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        playAdapter.setOnItemClickListener(new HomeServerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, ServiceDetailActivity2.class);
                intent.putExtra(ServiceDetailActivity2.SERVICEID,playAdapter.getData(position).getId());
                startActivity(intent);

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

                serverListPresenter.serveGuideServeOrderList(0,5,1,city,1,0,0);
                mPresenter.orderReviewsSortTop(city);
                mPresenter.bannerFindByType(Constant.BANNER_HOME,0);
                mPresenter.orderCarouselOrder();
            }
        });
    }


    //城市选择
    private void cityPick(){
        Intent intent = new Intent(context, MyCityPickActivity.class);
        intent.putExtra(MyCityPickActivity.LOCATION,tv_city_pick.getText().toString());
        startActivity(intent);
        desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
            @Override
            public void accept(CityPickEvent cityPickEvent) throws Exception {
                desDisposable.dispose();
                if(TextUtils.isEmpty(cityPickEvent.getCity()))
                    return;

                tv_city_pick.setText(cityPickEvent.getCity());
                MySelfInfo.getInstance().setDefaultCity(cityPickEvent.getCity());
                city = cityPickEvent.getCity();

                isDynamicLoad =false;
                isBannerLoad = true;
                isGuideLoad = false;

                serverListPresenter.serveGuideServeOrderList(0,5,1,city,1,0,0);
                mPresenter.orderReviewsSortTop(city);

                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    //----------banner start
    public void initBanner(final List<BannerModel> homeBanners){
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
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if(TextUtils.isEmpty(homeBanners.get(position).getToUrl())) return;
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(Constant.EXTRA_URL,homeBanners.get(position).getToUrl());
                        startActivity(intent);
                    }
                })
                .setPointViewVisible(true) //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.oval_white_hollow, R.drawable.oval_theme_solid})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
                .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）设置为false后手点击后 停止轮播了
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            view_flipper.stopFlipping();
        }else{
            if(isflipper)
                view_flipper.startFlipping();
        }
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

        if(models.getList().size() == 0){
            view_empty_guide.setVisible(true);
            view_empty_guide.setEmptyData(R.mipmap.empty_home_guide,"向导正在招募中，要不要您换个城市试试~");
            view_empty_guide.setEmptyBackground(R.color.white);
        }else{
            view_empty_guide.setVisible(false);
        }
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
    public void orderCarouselOrderSuccess(List<NoticeItem> models) {
        initTextBannerData(models);
        addNotice();
    }

    @Override
    public void orderCarouselOrderFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void serveGuideServeOrderListSuccess(List<ServerDetailModel> datas) {

        isDynamicLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }

        playAdapter.setData(datas);

        if(datas.size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_follow,"这里还是空空哒~");
            view_empty.setEmptyBackground(R.color.white);
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void serveGuideServeOrderListFailed(String msg) {
        isDynamicLoad = true;
        if(isBannerLoad && isDynamicLoad && isGuideLoad){
            swipeRefreshLayout.setRefreshing(false);
            isLoad = false;
        }
        showLongToast(msg);
    }

}

