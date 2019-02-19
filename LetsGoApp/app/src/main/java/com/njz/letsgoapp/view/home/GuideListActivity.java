package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.GuideListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.other.ConfigModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideListContract;
import com.njz.letsgoapp.mvp.home.GuideListPresenter;
import com.njz.letsgoapp.mvp.other.ConfigContract;
import com.njz.letsgoapp.mvp.other.ConfigPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.other.GuideSearchActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;
import com.njz.letsgoapp.widget.MyGuideTab;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideListActivity extends BaseActivity implements View.OnClickListener, GuideListContract.View,ConfigContract.View {

    public static final String START_TIME ="START_TIME";
    public static final String END_TIME ="END_TIME";

    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public LoadMoreWrapper loadMoreWrapper;

    public GuideListAdapter mAdapter;

    public ImageView ivLeft;
    public TextView tvCityPick;

    public MyGuideTab myGuideTab;

    public Disposable desDisposable;

    public PopGuideList2 popGuideList;

    public GuideListPresenter mPresenter;
    public ConfigPresenter configPresenter;

    public Map<String, String> maps;
    public int type = Constant.GUIDE_TYPE_SYNTHESIZE;

    public String startTime;
    public String endTime;
    public String location;
    public int page;
    public int isLoadType = 1;//1下拉刷新，2上拉加载
    public boolean isLoad = false;//是否在加载，重复加载问题
    public TextView tv_search;

    @Override
    public void getIntentData() {
        super.getIntentData();
        startTime = intent.getStringExtra(START_TIME);
        endTime = intent.getStringExtra(END_TIME);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_list;
    }

    @Override
    public void initView() {

        hideTitleLayout();

        ivLeft = $(R.id.iv_left);
        tvCityPick = $(R.id.tv_city_pick);
        tv_search = $(R.id.tv_search);

        initTabLayout();

        tv_search.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        tvCityPick.setOnClickListener(this);

        initRecycler();
        initSwipeLayout();
    }

    public void initTabLayout() {
        myGuideTab = $(R.id.my_guide_tab);
        myGuideTab.setCallback(new MyGuideTab.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case MyGuideTab.MYGUIDETAB_SYNTHESIZE:
                        type = Constant.GUIDE_TYPE_SYNTHESIZE;
                        getRefreshData(type);
                        break;
                    case MyGuideTab.MYGUIDETAB_COUNT:
                        type = Constant.GUIDE_TYPE_COUNT;
                        getRefreshData(type);
                        break;
                    case MyGuideTab.MYGUIDETAB_SCORE:
                        type = Constant.GUIDE_TYPE_SCORE;
                        getRefreshData(type);
                        break;
                    case MyGuideTab.MYGUIDETAB_COMMENT:
                        type = Constant.GUIDE_TYPE_COMMENT;
                        getRefreshData(type);
                        break;
                    case MyGuideTab.MYGUIDETAB_SCREEN:
                        popGuideList.showPopupWindow(myGuideTab);
                        break;
                }
            }
        });

        popGuideList = new PopGuideList2(context, myGuideTab);
        if (!TextUtils.isEmpty(startTime)) {
            popGuideList.setTime(startTime, endTime);
            myGuideTab.setScreen(true);
            maps = new HashMap<>();
            maps.put("startTime", startTime);
            maps.put("startTime", endTime);
        }
        popGuideList.setSubmitLisener(new PopGuideList2.SubmitLisener() {
            @Override
            public void onSubmit(Map<String, String> result) {
                //设置选中，获取回调信息，服务器交互
                myGuideTab.setScreen(true);

                for (String key : result.keySet()) {
                    System.out.println("key= " + key + " and value= " + result.get(key));
                }

                if (result.size() > 0) {
                    maps = result;
                } else {
                    maps = null;
                }
                getRefreshData(type);
            }

            @Override
            public void onReset() {
                myGuideTab.setScreen(false);
                maps = null;
                getRefreshData(type);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (popGuideList != null && popGuideList.isShowing()) {
            popGuideList.dismissPopupWindow();
            return;
        }
        super.onBackPressed();
    }

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GuideListAdapter(activity, new ArrayList<GuideModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.GUIDEID, mAdapter.getDatas().get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData(type);
            }
        });

    }

    //初始化SwipeLayout
    public void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getRefreshData(type);
            }
        });
    }

    public void getRefreshData(int type) {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData(type);
    }

    public void getMoreData(int type) {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getData(type);
    }

    public void getData(int type){
        mPresenter.guideSortTop10ByLocation(location, type, Constant.DEFAULT_LIMIT,page, maps);
    }


    @Override
    public void initData() {
        location = MySelfInfo.getInstance().getDefaultCity();

        mPresenter = new GuideListPresenter(context, this);
        configPresenter = new ConfigPresenter(context, this);
        getRefreshData(type);
        tvCityPick.setText(location);

        List<String> values = new ArrayList<>();
        values.add(Constant.CONFIG_XB);
        values.add(Constant.CONFIG_DYNL);
        values.add(Constant.CONFIG_CYNX);
        values.add(Constant.CONFIG_FWLX);
        values.add(Constant.CONFIG_YYLX);
        configPresenter.guideGetGuideMacros(values);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_search:
                intent = new Intent(context, GuideSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_city_pick:
                intent = new Intent(context, MyCityPickActivity.class);
                intent.putExtra(MyCityPickActivity.LOCATION, tvCityPick.getText().toString());
                startActivity(intent);
                desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
                    @Override
                    public void accept(CityPickEvent cityPickEvent) throws Exception {
                        desDisposable.dispose();
                        if(TextUtils.isEmpty(cityPickEvent.getCity()))
                            return;
                        MySelfInfo.getInstance().setDefaultCity(cityPickEvent.getCity());
                        location = cityPickEvent.getCity();
                        tvCityPick.setText(cityPickEvent.getCity());
                        getRefreshData(type);

                    }
                });
                break;
        }
    }

    @Override
    public void guideSortTop10ByLocationSuccess(GuideListModel models) {
        List<GuideModel> datas = models.getList();

        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }
        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void guideSortTop10ByLocationFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    public void guideGetGuideMacrosSuccess(List<ConfigModel> models) {
        popGuideList.setConfigData(models);
    }

    @Override
    public void guideGetGuideMacrosFailed(String msg) {
        showShortToast(msg);
    }
}
