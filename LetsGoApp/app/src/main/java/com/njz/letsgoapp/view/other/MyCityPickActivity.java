package com.njz.letsgoapp.view.other;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.CityPickCityAdapter;
import com.njz.letsgoapp.adapter.order.CityPickProvinceAdapter;
import com.njz.letsgoapp.adapter.other.SearchAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.other.CityModel;
import com.njz.letsgoapp.bean.other.LocationModel;
import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.bean.other.SearchCityModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.map.LocationUtil;
import com.njz.letsgoapp.mvp.other.CitySearchContract;
import com.njz.letsgoapp.mvp.other.CitySearchPresenter;
import com.njz.letsgoapp.mvp.other.MyCityPickContract;
import com.njz.letsgoapp.mvp.other.MyCityPickPresenter;
import com.njz.letsgoapp.util.PermissionUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.widget.emptyView.EmptyView;
import com.njz.letsgoapp.widget.emptyView.EmptyView2;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
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
    LinearLayout ll_history;
    TagFlowLayout flowlayout_history;

    MyCityPickPresenter mPresenter;

    CityPickProvinceAdapter mAdapterProvince;
    CityPickCityAdapter mAdapterCity;

    List<ProvinceModel> provinces;
    int provinceIndex = -1;

    String location = "";
    String city = "";

    FrameLayout ll_search;
    CitySearchPresenter searchPresenter;
    SearchAdapter searchAdapter;

    EmptyView view_empty;
    EmptyView2 view_empty_city;

    TextView tv_default;

    private LocationUtil locationUtil;
    private boolean locationIsOk = false;

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

        view_empty = $(R.id.view_empty);
        view_empty_city = $(R.id.view_empty_city);

        ll_history = $(R.id.ll_history);
        flowlayout_history = $(R.id.flowlayout_history);

        ivLeft = $(R.id.iv_left);
        tv_default = $(R.id.tv_default);
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
        initFlow();

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
        initDefault();
    }

    public void initFlow() {
        final List<String> lists = MySelfInfo.getInstance().getSearchCity();
        if(lists.size() == 0){
            ll_history.setVisibility(View.GONE);
            return;
        }

        final LayoutInflater mInflater = LayoutInflater.from(activity);
        TagAdapter adapter1 = new TagAdapter<String>(lists) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_search_city, flowlayout_history, false);
                tv.setText(s);
                return tv;
            }
        };
        flowlayout_history.setAdapter(adapter1);
        flowlayout_history.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                city = lists.get(position);
                RxBus2.getInstance().post(new CityPickEvent(city));
                finish();
                return false;
            }
        });
    }

    private void initDefault(){
        tv_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = Constant.DEFAULT_CITY;
                RxBus2.getInstance().post(new CityPickEvent(city));
                MySelfInfo.getInstance().setSearchCity(new ArrayList<String>());
                finish();
            }
        });
    }

    //初始化recyclerview
    private void initRecycler() {
        searchRecyclerView = $(R.id.recycler_view);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(activity, new ArrayList<SearchCityModel>());
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.setNestedScrollingEnabled(false);

        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                city = searchAdapter.getItem(position).getName();
                MySelfInfo.getInstance().addSearchCity(city);
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
                if(locationIsOk){
                    city = tv_location_city.getText().toString();
                    MySelfInfo.getInstance().addSearchCity(city);
                    RxBus2.getInstance().post(new CityPickEvent(city));
                    finish();
                }else{
                    if(!PermissionUtil.getInstance().isGpsAvailable(context)) return;
                    if(!PermissionUtil.getInstance().getPremission(context,PermissionUtil.PERMISSION_LOCATION,PermissionUtil.PERMISSION_LOCATION_CODE)) return;
                    getLocation();
                }
            }
        });

        locationUtil = new LocationUtil();
        getLocation();
    }

    public void getLocation(){
        tv_location_city.setText("定位中...");
        tv_location_city.setEnabled(false);
        locationIsOk = false;
        locationUtil.startLocation(new LocationUtil.LocationListener() {
            @Override
            public void getAdress(int code, LocationModel adress) {
                LogUtil.e("code:" + code + " adress:" + adress);
                if(code == 0){
                    tv_location_city.setText(adress.getCity());
                    tv_location_city.setEnabled(true);
                    locationIsOk = true;
                }else{
                    tv_location_city.setText("重新定位");
                    tv_location_city.setEnabled(true);
                    locationIsOk = false;
                }
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

                if(provinces.get(position).getTravelRegionEntitys().size() == 0){
                    view_empty_city.setVisible(true);
                    view_empty_city.setEmptyData(R.mipmap.empty_search,"要不...换个省会试试？");
                    view_empty_city.setEmptyBackground(R.color.white);
                }else{
                    view_empty_city.setVisible(false);
                }

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
                    MySelfInfo.getInstance().addSearchCity(city);
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

        if(models.size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_search,"要不...换个关键词试试？");
        }else{
            view_empty.setVisible(false);
        }

    }

    @Override
    public void regionFuzzyBySpellFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.PERMISSION_LOCATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//选择了不再提示按钮
                PermissionUtil.getInstance().showAccreditDialog(context, "温馨提示\n" +
                        "您需要同意那就走使用【定位】权限才能正常发布动态，" +
                        "由于您选择了【禁止（不再提示）】，将导致无法定位，" +
                        "需要到设置页面手动授权开启【定位】权限，才能发布动态。");
                return;
            }
        }
    }
}
