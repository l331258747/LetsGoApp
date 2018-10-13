package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.CityPickEvent;
import com.njz.letsgoapp.view.find.DynamicFragment;
import com.njz.letsgoapp.view.find.ReleaseDynamicActivity;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.other.MyCityPickActivity;
import com.njz.letsgoapp.widget.myTabLayout.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class FindFragment extends BaseFragment implements View.OnClickListener {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //    private TextView tvCityPick;
    private TextView tvSearch;
    private ImageView ivRelease;


    private Disposable desDisposable;

    private List<Fragment> mFragments;
    private String[] titles = {"全部", "关注"};

    private String city = MySelfInfo.getInstance().getDefaultCity();

    DynamicFragment dynamicAll;
    DynamicFragment dynamicFollow;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);
        tvSearch = $(R.id.tv_search);
        ivRelease = $(R.id.iv_release);

        ivRelease.setOnClickListener(this);
        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    setSearch(v.getText().toString());
                    AppUtils.HideKeyboard(tvSearch);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void initData() {
//        tvCityPick.setText(city);

        mFragments = new ArrayList<>();
        dynamicAll = (DynamicFragment) DynamicFragment.newInstance(DynamicFragment.DYNAMIC_ALL);
        dynamicFollow = (DynamicFragment) DynamicFragment.newInstance(DynamicFragment.DYNAMIC_FOLLOW);
        mFragments.add(dynamicAll);
        mFragments.add(dynamicFollow);

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_city_pick:
//                cityPick();
//                break;
            case R.id.iv_release:
                if (!MySelfInfo.getInstance().isLogin()) {//登录状态
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                startActivity(new Intent(context, ReleaseDynamicActivity.class));
                break;
        }
    }


    //城市选择
//    private void cityPick(){
//        Intent intent = new Intent(context, MyCityPickActivity.class);
//        intent.putExtra(MyCityPickActivity.LOCATION,tvCityPick.getText().toString());
//        startActivity(intent);
//        desDisposable = RxBus2.getInstance().toObservable(CityPickEvent.class, new Consumer<CityPickEvent>() {
//            @Override
//            public void accept(CityPickEvent cityPickEvent) throws Exception {
//                desDisposable.dispose();
//                if(TextUtils.isEmpty(cityPickEvent.getCity()))
//                    return;
//                tvCityPick.setText(cityPickEvent.getCity());
//                city = cityPickEvent.getCity();
//                setCityChange();
//
//            }
//        });
//    }

    public void setCityChange() {
        dynamicAll.setCityChange(city);
    }

    public void setSearch(String str) {
        dynamicAll.setSearch(str);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        dynamicAll.setHidden(hidden);
        dynamicFollow.setHidden(hidden);
    }


}
