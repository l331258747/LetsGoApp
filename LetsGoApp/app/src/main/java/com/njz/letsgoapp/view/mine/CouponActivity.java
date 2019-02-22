package com.njz.letsgoapp.view.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/21
 * Function:
 */

public class CouponActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragments;
    private String[] titles = {"未使用", "已使用", "已过期"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        showLeftAndTitle("我的优惠卷");

        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(CouponFragment.newInstance(1));
        mFragments.add(CouponFragment.newInstance(2));
        mFragments.add(CouponFragment.newInstance(3));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titles.length - 1);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
