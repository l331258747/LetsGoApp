package com.njz.letsgoapp.view.home;

import android.content.Intent;
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
import com.njz.letsgoapp.adapter.home.HomePlayAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.PlayData;
import com.njz.letsgoapp.bean.other.ConfigModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.other.ConfigContract;
import com.njz.letsgoapp.mvp.other.ConfigPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
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
 * Time: 2018/12/3
 * Function:
 */

public class PlayListActivity extends GuideListActivity {

    HomePlayAdapter playAdapter;

    public void initTabLayout() {
        myGuideTab = $(R.id.my_guide_tab);
        myGuideTab.setPriceLayout();
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
                    case MyGuideTab.MYGUIDETAB_PRICE:
                        type = Constant.GUIDE_TYPE_PRICE;
                        getRefreshData(type);
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

    //初始化recyclerview
    public void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        playAdapter = new HomePlayAdapter(activity, new ArrayList<PlayData>());
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
                getMoreData(type);
            }
        });

    }


    public void getData(int type){
        initPlayData();
    }

    @Override
    public void initData() {
        location = MySelfInfo.getInstance().getDefaultCity();

        configPresenter = new ConfigPresenter(context, this);
        getRefreshData(type);
        tvCityPick.setText(location);

        List<String> values = new ArrayList<>();
        values.add(Constant.CONFIG_XB);
        values.add(Constant.CONFIG_DYNL);
        values.add(Constant.CONFIG_CYNX);
//        values.add(Constant.CONFIG_FWLX);
        values.add(Constant.CONFIG_YYLX);
        configPresenter.guideGetGuideMacros(values);
    }

    public void initPlayData(){
        List<PlayData> datas = new ArrayList<>();
        PlayData data = new PlayData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543829470523&di=87de21d5e5ce4826a6d74b97deefb0b8&imgtype=0&src=http%3A%2F%2Fgotrip.zjol.com.cn%2Fxw14873%2Fycll14875%2F201710%2FW020171024603757884776.jpg",
                "长沙特色小吃游",300f,"长沙",4.8f,100,400);
        datas.add(data);
        datas.add(data);
        datas.add(data);

        playAdapter.setData(datas);
        swipeRefreshLayout.setRefreshing(false);

    }
}
