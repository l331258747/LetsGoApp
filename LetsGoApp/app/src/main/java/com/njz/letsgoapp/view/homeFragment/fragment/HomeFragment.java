package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import com.njz.letsgoapp.bean.home.GuideData;
import com.njz.letsgoapp.bean.home.HomeData;
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
import com.njz.letsgoapp.view.homeFragment.HomeActivity;

import java.lang.reflect.Field;
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
                .setPageIndicator(new int[]{R.drawable.oval_write_hollow, R.drawable.oval_theme_solid})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
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
            case R.id.rl_guide_title:
                startActivity(new Intent(context,GuideListActivity.class));
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

        tv_destination_content.setText("张家界");
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
        mAdapter = new HomeAdapter(activity, getHomeData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    HomeGuideAdapter mAdapterh;
    private void intRecyclerH(){
        recycler_view_h = $(R.id.recycler_view_h);
        recycler_view_h.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mAdapterh = new HomeGuideAdapter(activity, getDataH());
        recycler_view_h.setAdapter(mAdapterh);
        recycler_view_h.setNestedScrollingEnabled(false);
        mAdapterh.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                showShortToast("点击第" + position);
                startActivity(new Intent(context, GuideDetailActivity.class));
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

        initBanner(homeBanners);

        List<HomeData.Dynamic> guides = new ArrayList<>();
        HomeData.Dynamic dynamic0 = new HomeData.Dynamic();
        dynamic0.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic0.setName("那就走");
        dynamic0.setTime("23分钟前");
        dynamic0.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic0.setComment(900);
        dynamic0.setNice(2000);
        dynamic0.setLocation("长沙");
        List<String> dynamicImgs0 = new ArrayList<>();
        dynamicImgs0.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535108387016&di=4f7792890c26aeaec1f3d73331685601&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3D7c6c39c1ac86c9171c0e5a79a04515a3%2F80cb39dbb6fd526639b0e12da018972bd4073674.jpg");
        dynamic0.setDynamicImgs(dynamicImgs0);
        guides.add(dynamic0);

        HomeData.Dynamic dynamic1 = new HomeData.Dynamic();
        dynamic1.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic1.setName("那就走");
        dynamic1.setTime("23分钟前");
        dynamic1.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic1.setComment(900);
        dynamic1.setNice(2000);
        dynamic1.setLocation("长沙");
        List<String> dynamicImgs1 = new ArrayList<>();
        dynamicImgs1.add("http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1710/03/c5/61691946_1507022504917_mthumb.jpg");
        dynamic1.setDynamicImgs(dynamicImgs1);
        guides.add(dynamic1);

        HomeData.Dynamic dynamic2 = new HomeData.Dynamic();
        dynamic2.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic2.setName("那就走");
        dynamic2.setTime("23分钟前");
        dynamic2.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic2.setComment(900);
        dynamic2.setNice(2000);
        dynamic2.setLocation("长沙");
        List<String> dynamicImgs2 = new ArrayList<>();
        dynamicImgs2.add("http://file27.mafengwo.net/M00/23/A2/wKgB6lPu81-AMcwcAAmzfksNM3A45.jpeg");
        dynamicImgs2.add("http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg");
        dynamic2.setDynamicImgs(dynamicImgs2);
        guides.add(dynamic2);

        HomeData.Dynamic dynamic3 = new HomeData.Dynamic();
        dynamic3.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic3.setName("那就走");
        dynamic3.setTime("23分钟前");
        dynamic3.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic3.setComment(900);
        dynamic3.setNice(2000);
        dynamic3.setLocation("长沙");
        List<String> dynamicImgs3 = new ArrayList<>();
        dynamicImgs3.add("http://b2-q.mafengwo.net/s7/M00/D2/00/wKgB6lPuNOeAAQS0AArde4Ib0QM13.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamicImgs3.add("http://b4-q.mafengwo.net/s7/M00/D2/03/wKgB6lPuNOmAT30yAAH_P6uPNak27.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamicImgs3.add("http://n2-q.mafengwo.net/s7/M00/D1/F2/wKgB6lPuNN2AWV_HAB-DCItFN2w99.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamic3.setDynamicImgs(dynamicImgs3);
        guides.add(dynamic3);

        HomeData.Dynamic dynamic4 = new HomeData.Dynamic();
        dynamic4.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic4.setName("那就走");
        dynamic4.setTime("23分钟前");
        dynamic4.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic4.setComment(900);
        dynamic4.setNice(2000);
        dynamic4.setLocation("长沙");
        List<String> dynamicImgs4 = new ArrayList<>();
        dynamicImgs4.add("http://b4-q.mafengwo.net/s7/M00/D2/03/wKgB6lPuNOmAT30yAAH_P6uPNak27.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamicImgs4.add("http://p1-q.mafengwo.net/s7/M00/D2/11/wKgB6lPuNPiAdyLOAAlPcxhyWdI05.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamicImgs4.add("http://p1-q.mafengwo.net/s7/M00/D2/3D/wKgB6lPuNSCABs6xAANGgrlUXj856.jpeg?imageView2%2F2%2Fw%2F700%2Fh%2F600%2Fq%2F90%7CimageMogr2%2Fstrip%2Fquality%2F90");
        dynamicImgs4.add("http://img05.tooopen.com/images/20140510/sy_60792363469.jpg");
        dynamic4.setDynamicImgs(dynamicImgs4);
        guides.add(dynamic4);

        HomeData.Dynamic dynamic5 = new HomeData.Dynamic();
        dynamic5.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic5.setName("那就走");
        dynamic5.setTime("23分钟前");
        dynamic5.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic5.setComment(900);
        dynamic5.setNice(2000);
        dynamic5.setLocation("长沙");
        List<String> dynamicImgs5 = new ArrayList<>();
        dynamicImgs5.add("http://imgsrc.baidu.com/imgad/pic/item/7c1ed21b0ef41bd51b2dc86a5bda81cb39db3d35.jpg");
        dynamicImgs5.add("http://imgsrc.baidu.com/imgad/pic/item/1ad5ad6eddc451da85e2f318bcfd5266d0163228.jpg");
        dynamicImgs5.add("http://img3.imgtn.bdimg.com/it/u=4085031288,3873323766&fm=214&gp=0.jpg");
        dynamicImgs5.add("http://imgsrc.baidu.com/imgad/pic/item/d50735fae6cd7b8902253191052442a7d9330e52.jpg");
        dynamicImgs5.add("http://imgsrc.baidu.com/imgad/pic/item/1f178a82b9014a9014246ba2a3773912b31bee93.jpg");
        dynamic5.setDynamicImgs(dynamicImgs5);
        guides.add(dynamic5);


        HomeData.Dynamic dynamic6 = new HomeData.Dynamic();
        dynamic6.setHeadImg("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        dynamic6.setName("那就走");
        dynamic6.setTime("23分钟前");
        dynamic6.setContent("那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走那就走");
        dynamic6.setComment(900);
        dynamic6.setNice(2000);
        dynamic6.setLocation("长沙");
        List<String> dynamicImgs6 = new ArrayList<>();
        dynamicImgs6.add("http://imgsrc.baidu.com/imgad/pic/item/5fdf8db1cb1349547414c5435d4e9258d1094a8a.jpg");
        dynamicImgs6.add("http://imgsrc.baidu.com/imgad/pic/item/d0c8a786c9177f3ef70febf87acf3bc79f3d560f.jpg");
        dynamicImgs6.add("http://imgsrc.baidu.com/imgad/pic/item/a8773912b31bb05154a7d2dd3c7adab44bede040.jpg");
        dynamicImgs6.add("http://pic25.nipic.com/20121107/668573_150230597135_2.jpg");
        dynamicImgs6.add("http://img06.tooopen.com/images/20170523/tooopen_sy_211650058843.jpg");
        dynamicImgs6.add("http://imgsrc.baidu.com/imgad/pic/item/7c1ed21b0ef41bd51b2dc86a5bda81cb39db3d35.jpg");
        dynamic6.setDynamicImgs(dynamicImgs6);
        guides.add(dynamic6);

        return new HomeData(homeBanners, guides);
    }

    public List<GuideData> getDataH() {
        List<GuideData> guideDatas = new ArrayList<>();
        GuideData guideData = new GuideData();
        guideData.setComment(400);
        guideData.setContent("aegaegjaklegjalkag");
        guideData.setHeadUrl("http://s9.rr.itc.cn/r/wapChange/20164_30_21/a2tklm523975660855.jpg");
        guideData.setName("导游");
        guideData.setPrice(390);
        List<String> serviceItmes = new ArrayList<>();
        serviceItmes.add("向导陪游");
        serviceItmes.add("包车服务");
        serviceItmes.add("代订门票");
        guideData.setServiceItems(serviceItmes);
        guideData.setServiceNum(300);
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

