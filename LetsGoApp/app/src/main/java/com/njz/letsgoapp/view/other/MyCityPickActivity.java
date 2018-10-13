package com.njz.letsgoapp.view.other;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.CityPickCityAdapter;
import com.njz.letsgoapp.adapter.order.CityPickProvinceAdapter;
import com.njz.letsgoapp.adapter.other.SearchAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.other.CityModel;
import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.bean.other.SearchCityModel;
import com.njz.letsgoapp.mvp.other.CitySearchContract;
import com.njz.letsgoapp.mvp.other.CitySearchPresenter;
import com.njz.letsgoapp.mvp.other.MyCityPickContract;
import com.njz.letsgoapp.mvp.other.MyCityPickPresenter;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class MyCityPickActivity extends BaseActivity implements MyCityPickContract.View,CitySearchContract.View {

    public static final String LOCATION = "LOCATION";

    TextView tv_search, tv_location_city;
    ImageView ivLeft;
    RecyclerView recycler_view_province, recycler_view_city,searchRecyclerView;

    MyCityPickPresenter mPresenter;

    CityPickProvinceAdapter mAdapterProvince;
    CityPickCityAdapter mAdapterCity;

    List<ProvinceModel> provinces;
    int provinceIndex = -1;

    String location = "";
    String city = "";

    LinearLayout ll_search;
    CitySearchPresenter searchPresenter;
    SearchAdapter searchAdapter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        location = intent.getStringExtra(LOCATION);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_city_pick;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        ivLeft = $(R.id.iv_left);
        tv_location_city = $(R.id.tv_location_city);
        ll_search = $(R.id.ll_search);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initProvinceRecycler();
        initCityRecycler();
        initRecycler();

        tv_search = $(R.id.tv_search);

        ll_search.setVisibility(View.GONE);
        tv_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(v.getText().toString())){
                        searchPresenter.regionFuzzyBySpell(v.getText().toString());
                        ll_search.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        searchRecyclerView = $(R.id.recycler_view);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(activity, new ArrayList<SearchCityModel>());
        searchRecyclerView.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                city = searchAdapter.getItem(position).getName();
                RxBus2.getInstance().post(new CityPickEvent(city));
                finish();
            }
        });
    }

    @Override
    public void initData() {
        tv_location_city.setText(location);

        searchPresenter = new CitySearchPresenter(context,this);
        mPresenter = new MyCityPickPresenter(context, this);

        mPresenter.regionFindProAndCity();

        tv_location_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = tv_location_city.getText().toString();
                RxBus2.getInstance().post(new CityPickEvent(city));
                finish();
            }
        });
    }

    private void initProvinceRecycler() {
        recycler_view_province = $(R.id.recycler_view_province);
        recycler_view_province.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mAdapterProvince = new CityPickProvinceAdapter(context, new ArrayList<ProvinceModel>());
        recycler_view_province.setAdapter(mAdapterProvince);

        mAdapterProvince.setOnItemClickListener(new CityPickProvinceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mAdapterCity.setDatas(provinces.get(position).getTravelRegionEntitys());
                provinceIndex = position;
            }
        });
    }

    private void initCityRecycler() {
        recycler_view_city = $(R.id.recycler_view_city);
        recycler_view_city.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mAdapterCity = new CityPickCityAdapter(context, new ArrayList<CityModel>());
        recycler_view_city.setAdapter(mAdapterCity);

        mAdapterCity.setOnItemClickListener(new CityPickCityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if(provinceIndex != -1){
                    String cityName = provinces.get(provinceIndex).getTravelRegionEntitys().get(position).getName();
                    city = cityName;
                    if(city.endsWith("市"))
                        city = city.substring(0,city.length() - 1);
                    RxBus2.getInstance().post(new CityPickEvent(city));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (ll_search != null && ll_search.getVisibility() == View.VISIBLE) {
            ll_search.setVisibility(View.GONE);
            tv_search.setText("");
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void regionFindProAndCitySuccess(List<ProvinceModel> models) {
        provinces = models;
        mAdapterProvince.setDatas(models);
    }

    @Override
    public void regionFindProAndCityFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    protected void onDestroy() {
        if(TextUtils.isEmpty(city)){
            RxBus2.getInstance().post(new CityPickEvent(city));
        }
        super.onDestroy();
    }

    @Override
    public void regionFuzzyBySpellSuccess(List<SearchCityModel> models) {
        searchAdapter.setData(models);
    }

    @Override
    public void regionFuzzyBySpellFailed(String msg) {
        showShortToast(msg);
    }
}
