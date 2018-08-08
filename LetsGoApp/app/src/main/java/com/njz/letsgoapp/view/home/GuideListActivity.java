package com.njz.letsgoapp.view.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.GuideListAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.GuideData;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideListActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    static RecyclerView recyclerView;

    GuideListAdapter mAdapter;
    GuideData guideList;

    ImageView ivLeft;
    TextView tvCityPick;
    TextView tvSearch;

    Disposable desDisposable;

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


    //初始化recyclerview
    private void initRecycler() {
        List<GuideData> guideDatas = new ArrayList<>();

        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GuideListAdapter(activity, guideDatas);
        recyclerView.setAdapter(mAdapter);

    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setData(getData());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void initData() {

    }


    public List<GuideData> getData() {
        List<GuideData> guideDatas = new ArrayList<>();
        GuideData guideData = new GuideData();
        guideData.setComment("" + 400);
        guideData.setContent("aegaegjaklegjalkag");
        guideData.setHeadUrl("http://img2.imgtn.bdimg.com/it/u=668252697,2695635115&fm=214&gp=0.jpg");
        guideData.setName("导游");
        guideData.setPrice(390);
        List<String> serviceItmes = new ArrayList<>();
        serviceItmes.add("向导陪游");
        serviceItmes.add("包车服务");
        serviceItmes.add("代订门票");
        guideData.setServiceItems(serviceItmes);
        guideData.setServiceNum(300 + "");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
}
