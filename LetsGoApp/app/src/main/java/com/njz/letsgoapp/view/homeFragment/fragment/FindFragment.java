package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.view.find.DynamicFragment;
import com.njz.letsgoapp.view.find.ReleaseDynamicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class FindFragment extends BaseFragment implements View.OnClickListener {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView tvCityPick, tvSearch;
    private ImageView ivRelease;


    private List<Fragment> mFragments;
    private String[] titles = {"全部", "关注"};


    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);
        tvCityPick = $(R.id.tv_city_pick);
        tvSearch = $(R.id.tv_search);
        ivRelease = $(R.id.iv_release);

        tvCityPick.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        ivRelease.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(DynamicFragment.newInstance(DynamicFragment.DYNAMIC_ALL));
        mFragments.add(DynamicFragment.newInstance(DynamicFragment.DYNAMIC_FOLLOW));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_city_pick:

                break;
            case R.id.tv_search:

                break;
            case R.id.iv_release:
                startActivity(new Intent(context, ReleaseDynamicActivity.class));
                break;
        }
    }
}
