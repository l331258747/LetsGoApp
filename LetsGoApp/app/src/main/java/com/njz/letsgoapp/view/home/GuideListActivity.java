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
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideListContract;
import com.njz.letsgoapp.mvp.home.GuideListPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;
import com.njz.letsgoapp.widget.MyGuideTab;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideListActivity extends BaseActivity implements View.OnClickListener, GuideListContract.View {

    public static final String START_TIME ="START_TIME";
    public static final String END_TIME ="END_TIME";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;

    GuideListAdapter mAdapter;

    ImageView ivLeft;
    TextView tvCityPick;
    TextView tvSearch;

    MyGuideTab myGuideTab;

    Disposable desDisposable;

    PopGuideList popGuideList;

    GuideListPresenter mPresenter;

    Map<String, String> maps;
    int type = Constant.GUIDE_TYPE_SYNTHESIZE;

    String startTime;
    String endTime;
    String location;
    int page;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

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
        tvSearch = $(R.id.tv_search);

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

        popGuideList = new PopGuideList(context, myGuideTab);
        if (!TextUtils.isEmpty(startTime)) {
            popGuideList.setTime(startTime, endTime);
            myGuideTab.setScreen(true);
            maps = new HashMap<>();
            maps.put("startTime", startTime);
            maps.put("startTime", endTime);
        }
        popGuideList.setSubmitLisener(new PopGuideList.SubmitLisener() {
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

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCityPick.setOnClickListener(this);
        tvSearch.setOnClickListener(this);


        initRecycler();
        initSwipeLayout();
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
    private void initRecycler() {
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
                LogUtil.e(mAdapter.getDatas().get(position).getGuideId() + "");
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
    private void initSwipeLayout() {
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

    private void getRefreshData(int type) {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        mPresenter.guideSortTop10ByLocation(location, type, Constant.DEFAULT_LIMIT, Constant.DEFAULT_PAGE, maps);
    }

    private void getMoreData(int type) {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        mPresenter.guideSortTop10ByLocation(location, type, Constant.DEFAULT_LIMIT, page, maps);
    }


    @Override
    public void initData() {
        location = MySelfInfo.getInstance().getDefaultCity();

        mPresenter = new GuideListPresenter(context, this);
        getRefreshData(type);
        tvCityPick.setText(location);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
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
            case R.id.tv_search:

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
}
