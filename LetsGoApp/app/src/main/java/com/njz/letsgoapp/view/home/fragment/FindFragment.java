package com.njz.letsgoapp.view.home.fragment;

import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class FindFragment extends BaseFragment{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        TextView homeIv = $(R.id.home_iv);
        homeIv.setText("发现");

    }

    @Override
    public void initData() {

    }
}
