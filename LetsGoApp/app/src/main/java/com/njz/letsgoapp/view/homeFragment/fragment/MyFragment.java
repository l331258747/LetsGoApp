package com.njz.letsgoapp.view.homeFragment.fragment;

import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class MyFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        TextView homeIv = $(R.id.home_iv);
        homeIv.setText("我的");

    }

    @Override
    public void initData() {

    }
}
