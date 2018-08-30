package com.njz.letsgoapp.view.other;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.CityPickCityAdapter;
import com.njz.letsgoapp.adapter.order.CityPickProvinceAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.other.CityModel;
import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.mvp.other.MyCityPickContract;
import com.njz.letsgoapp.mvp.other.MyCityPickPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/30
 * Function:
 */

public class MyCityPickActivity extends BaseActivity implements MyCityPickContract.View {

    TextView tv_search, tv_location_city;
    ImageView ivLeft;
    RecyclerView recycler_view_province, recycler_view_city;

    MyCityPickPresenter mPresenter;

    CityPickProvinceAdapter mAdapterProvince;
    CityPickCityAdapter mAdapterCity;

    List<ProvinceModel> provinces;
    int provinceIndex = -1;

    @Override
    public void getIntentData() {
        super.getIntentData();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_city_pick;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        ivLeft = $(R.id.iv_left);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initProvinceRecycler();
        initCityRecycler();

    }

    @Override
    public void initData() {
        mPresenter = new MyCityPickPresenter(context, this);

        mPresenter.regionFindProAndCity();
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
                showShortToast(provinces.get(position).getProName());
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
                    provinces.get(provinceIndex).getTravelRegionEntitys().get(position).getName();
                    showShortToast(provinces.get(provinceIndex).getTravelRegionEntitys().get(position).getName());
                }
            }
        });
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
}
