package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.login.ModifyPasswordActivity;
import com.njz.letsgoapp.view.login.ModifyPhoneActivity;
import com.njz.letsgoapp.view.mine.MyInfoActivity;
import com.njz.letsgoapp.view.mine.SystemSettingActivity;
import com.njz.letsgoapp.widget.MineItemView;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {

    MineItemView mine_bind, mine_info, mine_modify, mine_commont, mine_custom, mine_setting;

    ImageView iv_head;
    TextView tv_name;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        iv_head = $(R.id.iv_head);
        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532339453709&di=c506e751bd24c08cb2221d51ac3300c7&imgtype=0&src=http%3A%2F%2Fimg.80tian.com%2Fblog%2F201403%2F20140323170732_1145.jpg";
        GlideUtil.LoadCircleImage(context, photo, iv_head);

        tv_name = $(R.id.tv_name);

        mine_bind = $(R.id.mine_bind);
        mine_info = $(R.id.mine_info);
        mine_modify = $(R.id.mine_modify);
        mine_commont = $(R.id.mine_commont);
        mine_custom = $(R.id.mine_custom);
        mine_setting = $(R.id.mine_setting);

        mine_bind.setOnClickListener(this);
        mine_info.setOnClickListener(this);
        mine_modify.setOnClickListener(this);
        mine_commont.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        mine_custom.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_bind:
                startActivity(new Intent(context,ModifyPhoneActivity.class));
                break;
            case R.id.mine_info:
                startActivity(new Intent(context,MyInfoActivity.class));
                break;
            case R.id.mine_modify:
                startActivity(new Intent(context,ModifyPasswordActivity.class));
                break;
            case R.id.mine_commont:

                break;
            case R.id.mine_custom:

                break;
            case R.id.mine_setting:
                startActivity(new Intent(context,SystemSettingActivity.class));
                break;

        }
    }
}
