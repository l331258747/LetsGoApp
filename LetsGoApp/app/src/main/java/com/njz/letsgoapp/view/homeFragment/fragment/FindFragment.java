package com.njz.letsgoapp.view.homeFragment.fragment;

import android.os.Build;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.view.homeFragment.HomeActivity;

import java.lang.reflect.Field;

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
