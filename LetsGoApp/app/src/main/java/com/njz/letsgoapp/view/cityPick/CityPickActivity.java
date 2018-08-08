package com.njz.letsgoapp.view.cityPick;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/3
 * Function:
 */

public class CityPickActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new View(this));

        ActivityCollect.getAppCollect().addActivity(this);

        setTheme(R.style.CustomTheme);

        initView();
    }

    public void initView() {

        List<HotCity> hotCities = new ArrayList<>();
                hotCities.add(new HotCity("北京", "北京", "101010100"));
                hotCities.add(new HotCity("上海", "上海", "101020100"));
                hotCities.add(new HotCity("广州", "广东", "101280101"));
                hotCities.add(new HotCity("深圳", "广东", "101280601"));
                hotCities.add(new HotCity("杭州", "浙江", "101210101"));

                List<City> citys = new ArrayList<>();
                citys.add(new City("北京", "北京","beijing", "101010100"));
                citys.add(new City("北京", "北京","beijing", "101010100"));
                citys.add(new City("北京", "北京","beijing", "101010100"));
                citys.add(new City("北京", "北京","beijing", "101010100"));
                citys.add(new City("北京", "北京","beijing", "101010100"));
                citys.add(new City("上海", "上海","shanghai", "101020100"));
                citys.add(new City("上海", "上海","shanghai", "101020100"));
                citys.add(new City("上海", "上海","shanghai", "101020100"));
                citys.add(new City("上海", "上海","shanghai", "101020100"));
                citys.add(new City("上海", "上海","shanghai", "101020100"));
                citys.add(new City("广州", "广东","guangzhou", "101280101"));
                citys.add(new City("广州", "广东","guangzhou", "101280101"));
                citys.add(new City("广州", "广东","guangzhou", "101280101"));
                citys.add(new City("广州", "广东","guangzhou", "101280101"));
                citys.add(new City("广州", "广东","guangzhou", "101280101"));
                citys.add(new City("深圳", "广东","shenzhen", "101280601"));
                citys.add(new City("深圳", "广东","shenzhen", "101280601"));
                citys.add(new City("深圳", "广东","shenzhen", "101280601"));
                citys.add(new City("深圳", "广东","shenzhen", "101280601"));
                citys.add(new City("深圳", "广东","shenzhen", "101280601"));
                citys.add(new City("杭州", "浙江","hangzhou", "101210101"));
                citys.add(new City("杭州", "浙江","hangzhou", "101210101"));
                citys.add(new City("杭州", "浙江","hangzhou", "101210101"));
                citys.add(new City("杭州", "浙江","hangzhou", "101210101"));
                citys.add(new City("杭州", "浙江","hangzhou", "101210101"));


                CityPicker.getInstance()
                        .setFragmentManager(getSupportFragmentManager())	//此方法必须调用
                        .enableAnimation(true)	//启用动画效果
//                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)	//自定义动画
                        .setAnimationStyle(R.style.CustomAnim)	//自定义动画
                        .setCitys(citys)
//                        .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，默认为null（定位失败）
                        .setHotCities(hotCities)	//指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                ToastUtil.showShortToast(AppUtils.getContext(),data.getName());
                                RxBus2.getInstance().post(new CityPickEvent(data.getName()));
                            }

                            @Override
                            public void onLocate() {
                            }
                        })
                        .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

}
