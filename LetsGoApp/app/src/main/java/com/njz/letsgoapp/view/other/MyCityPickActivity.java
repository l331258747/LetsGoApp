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
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;

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

    String location = "";

    @Override
    public void getIntentData() {
        super.getIntentData();
        location = intent.getStringExtra("location");
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
        tv_location_city.setText(location);


        mPresenter = new MyCityPickPresenter(context, this);

        mPresenter.regionFindProAndCity();

        tv_location_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus2.getInstance().post(new CityPickEvent(tv_location_city.getText().toString()));
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
                    String cityName = provinces.get(provinceIndex).getTravelRegionEntitys().get(position).getName();
                    showShortToast(cityName);
                    String city = cityName;
                    if(city.endsWith("市"))
                        city = city.substring(0,city.length() - 1);
                    RxBus2.getInstance().post(new CityPickEvent(city));
                    finish();
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
