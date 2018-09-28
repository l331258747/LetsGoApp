package com.njz.letsgoapp.view.mine;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.BaseFragmentAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.widget.myTabLayout.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mFragments;
    private String[] titles = {"谁评论过我", "我评论过谁"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_comment;
    }

    @Override
    public void initView() {
        showLeftAndTitle("我的评论");

        mTabLayout = $(R.id.tabs);
        mViewPager = $(R.id.viewpager);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(MyCommentFragment.newInstance(1));
        mFragments.add(MyCommentFragment.newInstance(2));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titles.length - 1);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
