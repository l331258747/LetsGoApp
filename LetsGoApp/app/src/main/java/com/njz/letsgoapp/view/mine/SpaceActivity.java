package com.njz.letsgoapp.view.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class SpaceActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private DynamicAdapter mAdapter;
    private ImageView ivHead, ivSex;
    private TextView tvFans, tvAge, tvExplain;
    private ServiceTagView serviceTagView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_space;
    }

    @Override
    public void initView() {
        ivHead = $(R.id.iv_head);
        ivSex = $(R.id.iv_sex);
        tvFans = $(R.id.tv_fans);
        tvAge = $(R.id.tv_age);
        tvExplain = $(R.id.tv_explain);
        serviceTagView = $(R.id.service_tag_view);

        initRecycler();
    }

    @Override
    public void initData() {
        //TODO 个人信息，个人动态
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


    }
}
