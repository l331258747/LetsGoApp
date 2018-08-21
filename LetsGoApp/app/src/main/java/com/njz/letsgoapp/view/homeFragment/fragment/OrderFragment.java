package com.njz.letsgoapp.view.homeFragment.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.view.order.OrderWaitFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class OrderFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragments;
    private String[] titles = {"待付款", "已付款", "待点评", "已退款"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tablayout;
    }

    @Override
    public void initView() {

        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);

    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(OrderWaitFragment.newInstance());
        mFragments.add(OrderWaitFragment.newInstance());
        mFragments.add(OrderWaitFragment.newInstance());
        mFragments.add(OrderWaitFragment.newInstance());

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}