package com.njz.letsgoapp.view.server;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.HomePlayAdapter;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.other.ConfigPresenter;
import com.njz.letsgoapp.mvp.server.ServerListContract;
import com.njz.letsgoapp.mvp.server.ServerListPresenter;
import com.njz.letsgoapp.mvp.server.ServerListScreenContract;
import com.njz.letsgoapp.mvp.server.ServerListScreenPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.home.GuideListActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;
import com.njz.letsgoapp.view.other.SearchActivity;
import com.njz.letsgoapp.widget.MyGuideTab;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class PlayListActivity extends GuideListActivity implements ServerListScreenContract.View{

    HomePlayAdapter playAdapter;
    ServerListScreenPresenter serverListPresenter;

    String value;
    int order = Constant.GUIDE_TYPE_SYNTHESIZE;

    @Override
    public void getIntentData() {
        super.getIntentData();
        value = intent.getStringExtra("SERVER_VALUE");
    }

    public void initTabLayout() {
        myGuideTab = $(R.id.my_guide_tab);
        myGuideTab.setPriceLayout();
        myGuideTab.setCallback(new MyGuideTab.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case MyGuideTab.MYGUIDETAB_SYNTHESIZE:
                        order = Constant.GUIDE_TYPE_SYNTHESIZE;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_COUNT:
                        order = Constant.GUIDE_TYPE_COUNT;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_SCORE:
                        order = Constant.GUIDE_TYPE_SCORE;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_COMMENT:
                        order = Constant.GUIDE_TYPE_COMMENT;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_PRICE:
                        order = Constant.GUIDE_TYPE_PRICE;
                        getRefreshData();
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
                getRefreshData();
            }

            @Override
            public void onReset() {
                myGuideTab.setScreen(false);
                maps = null;
                getRefreshData();
            }
        });
    }

    //初始化recyclerview
    @Override
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        playAdapter = new HomePlayAdapter(activity, new ArrayList<ServerDetailMedel>());
        loadMoreWrapper = new LoadMoreWrapper(playAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        page = Constant.DEFAULT_PAGE;

        playAdapter.setOnItemClickListener(new HomePlayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, ServiceDetailActivity2.class);
                intent.putExtra(ServiceDetailActivity.SERVICEID,playAdapter.getData(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });

    }

    @Override
    public void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getRefreshData();
            }
        });
    }


    public void getData(){
        serverListPresenter.serveGuideServeFilterList(value,Constant.DEFAULT_LIMIT,page,location,0,0,0,order,maps);
    }

    @Override
    public void initData() {
        location = MySelfInfo.getInstance().getDefaultCity();

        configPresenter = new ConfigPresenter(context, this);
        serverListPresenter = new ServerListScreenPresenter(context,this);

        getRefreshData();
        tvCityPick.setText(location);

//        List<String> values = new ArrayList<>();
//        values.add(Constant.CONFIG_XB);
//        values.add(Constant.CONFIG_DYNL);
//        values.add(Constant.CONFIG_CYNX);
////        values.add(Constant.CONFIG_FWLX);
//        values.add(Constant.CONFIG_YYLX);
//        configPresenter.guideGetGuideMacros(values);


        if(TextUtils.equals(value,Constant.SERVER_TYPE_GUIDE) || TextUtils.equals(value,Constant.SERVER_TYPE_FEATURE)){
            List<String> values = new ArrayList<>();
            values.add(Constant.CONFIG_XB);
            values.add(Constant.CONFIG_DYNL);
            values.add(Constant.CONFIG_CYNX);
            values.add(Constant.CONFIG_YYLX);
            configPresenter.guideGetGuideMacros(values);
            popGuideList.setLayoutType(3);
        }else if(TextUtils.equals(value,Constant.SERVER_TYPE_HOTEL) || TextUtils.equals(value,Constant.SERVER_TYPE_TICKET)){
            List<String> values = new ArrayList<>();
            values.add(Constant.CONFIG_XB);
            values.add(Constant.CONFIG_DYNL);
            values.add(Constant.CONFIG_CYNX);
            configPresenter.guideGetGuideMacros(values);
            popGuideList.setLayoutType(2);
        }else if(TextUtils.equals(value,Constant.SERVER_TYPE_CUSTOM) || TextUtils.equals(value,Constant.SERVER_TYPE_CAR)){
            List<String> values = new ArrayList<>();
            values.add(Constant.CONFIG_XB);
            values.add(Constant.CONFIG_DYNL);
            values.add(Constant.CONFIG_CYNX);
            values.add(Constant.CONFIG_YYLX);
            configPresenter.guideGetGuideMacros(values);
            popGuideList.setLayoutType(2);
        }


    }

    public void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getData();
    }

    public void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getData();
    }


    @Override
    public void serveGuideServeFilterListSuccess(List<ServerDetailMedel> datas) {
        if (isLoadType == 1) {
            playAdapter.setData(datas);
        } else {
            playAdapter.addData(datas);
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
    public void serveGuideServeFilterListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_search:
                intent = new Intent(context, SearchActivity.class);
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
                        getRefreshData();

                    }
                });
                break;
        }
    }
}
