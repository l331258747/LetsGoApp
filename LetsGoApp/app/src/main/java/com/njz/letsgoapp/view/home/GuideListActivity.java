package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.GuideListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.home.GuideListContract;
import com.njz.letsgoapp.mvp.home.GuideListPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.widget.MyGuideTab;
import com.njz.letsgoapp.widget.popupwindow.PopGuideList;

import java.util.ArrayList;
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

public class GuideListActivity extends BaseActivity implements View.OnClickListener,GuideListContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    GuideListAdapter mAdapter;

    ImageView ivLeft;
    TextView tvCityPick;
    TextView tvSearch;

    MyGuideTab myGuideTab;

    Disposable desDisposable;

    PopGuideList popGuideList;

    GuideListPresenter mPresenter;

    List<GuideModel> datas;

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
                        getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_SYNTHESIZE);
                        break;
                    case MyGuideTab.MYGUIDETAB_COUNT:
                        getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_COUNT);
                        break;
                    case MyGuideTab.MYGUIDETAB_SCORE:
                        getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_SCORE);
                        break;
                    case MyGuideTab.MYGUIDETAB_COMMENT:
                        getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_COMMENT);
                        break;
                    case MyGuideTab.MYGUIDETAB_SCREEN:
                        showShortToast("筛选");

                        if (popGuideList == null){
                            popGuideList = new PopGuideList(context, myGuideTab);

                            popGuideList.setSubmitLisener(new PopGuideList.SubmitLisener() {
                                @Override
                                public void onSubmit(ArrayMap<String,String> result) {
                                    //设置选中，获取回调信息，服务器交互
                                    myGuideTab.setScreen(true);

                                    for (String key : result.keySet()) {
                                        System.out.println("key= "+ key + " and value= " + result.get(key));
                                    }

                                }

                                @Override
                                public void onReset() {
                                    myGuideTab.setScreen(false);
                                }
                            });
                        }

                        popGuideList.showPopupWindow(myGuideTab);
                        break;
                }
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
        if(popGuideList != null && popGuideList.isShowing()){
            popGuideList.dismissPopupWindow();
            return;
        }
        super.onBackPressed();
    }

    //初始化recyclerview
    private void initRecycler() {
        datas = new ArrayList<>();
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GuideListAdapter(activity, datas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, GuideDetailActivity.class);
                LogUtil.e(datas.get(position).getGuideId() + "");
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
                getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_SYNTHESIZE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getGuideSortTop10ByLocation(int type){
        mPresenter.guideSortTop10ByLocation(Constant.DEFAULT_CITY,type, Constant.DEFAULT_LIMIT,Constant.DEFAULT_PAGE);
    }


    @Override
    public void initData() {
        mPresenter = new GuideListPresenter(context,this);

        getGuideSortTop10ByLocation(Constant.GUIDE_TYPE_SYNTHESIZE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_city_pick:
                startActivity(new Intent(context, CityPickActivity.class));
                activity.overridePendingTransition(0, 0);
                desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
                    @Override
                    public void accept(CityPickEvent cityPickEvent) throws Exception {
                        tvCityPick.setText(cityPickEvent.getCity());
                        desDisposable.dispose();
                    }
                });
                break;
            case R.id.tv_search:

                break;
        }
    }

    @Override
    public void guideSortTop10ByLocationSuccess(GuideListModel models) {
        datas = models.getList();
        mAdapter.setData(datas);
    }

    @Override
    public void guideSortTop10ByLocationFailed(String msg) {
        showShortToast(msg);
    }
}
