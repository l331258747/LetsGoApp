package com.njz.letsgoapp.view.server;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.HomePlayAdapter;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.server.PlayModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.other.ConfigPresenter;
import com.njz.letsgoapp.mvp.server.ServerListContract;
import com.njz.letsgoapp.mvp.server.ServerListPresenter;
import com.njz.letsgoapp.view.home.GuideListActivity;
import com.njz.letsgoapp.widget.MyGuideTab;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class PlayListActivity extends GuideListActivity implements ServerListContract.View{

    HomePlayAdapter playAdapter;
    ServerListPresenter serverListPresenter;

    String value;

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
                        type = Constant.GUIDE_TYPE_SYNTHESIZE;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_COUNT:
                        type = Constant.GUIDE_TYPE_COUNT;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_SCORE:
                        type = Constant.GUIDE_TYPE_SCORE;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_COMMENT:
                        type = Constant.GUIDE_TYPE_COMMENT;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_PRICE:
                        type = Constant.GUIDE_TYPE_PRICE;
                        getRefreshData();
                        break;
                    case MyGuideTab.MYGUIDETAB_SCREEN:
                        popGuideList.showPopupWindow(myGuideTab);
                        break;
                }
            }
        });

        popGuideList = new PopGuideList2(context, myGuideTab);
        popGuideList.setLayoutType(1,2);
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
        playAdapter = new HomePlayAdapter(activity, new ArrayList<PlayModel>());
        loadMoreWrapper = new LoadMoreWrapper(playAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        page = Constant.DEFAULT_PAGE;

        playAdapter.setOnItemClickListener(new HomePlayAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
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
        serverListPresenter.serveGuideServeOrderList(value,Constant.DEFAULT_LIMIT,page,location,0,0,0);
    }

    @Override
    public void initData() {
        location = MySelfInfo.getInstance().getDefaultCity();

        configPresenter = new ConfigPresenter(context, this);
        serverListPresenter = new ServerListPresenter(context,this);

        getRefreshData();
        tvCityPick.setText(location);

        List<String> values = new ArrayList<>();
        values.add(Constant.CONFIG_XB);
        values.add(Constant.CONFIG_DYNL);
        values.add(Constant.CONFIG_CYNX);
//        values.add(Constant.CONFIG_FWLX);
        values.add(Constant.CONFIG_YYLX);
        configPresenter.guideGetGuideMacros(values);
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
    public void serveGuideServeOrderListSuccess(List<PlayModel> datas) {
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
    public void serveGuideServeOrderListFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }
}
