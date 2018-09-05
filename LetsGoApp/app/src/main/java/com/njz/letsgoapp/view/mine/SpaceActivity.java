package com.njz.letsgoapp.view.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.mine.SpaceContract;
import com.njz.letsgoapp.mvp.mine.SpacePresenter;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class SpaceActivity extends BaseActivity implements SpaceContract.View{

    private RecyclerView recyclerView;
    private DynamicAdapter mAdapter;
    private ImageView ivHead, ivSex;
    private TextView tvFans, tvAge, tvExplain,tvName;
    private ServiceTagView serviceTagView;

    private SpacePresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_space;
    }

    @Override
    public void initView() {

        showLeftAndTitle("个人主页");

        ivHead = $(R.id.iv_head);
        ivSex = $(R.id.iv_sex);
        tvFans = $(R.id.tv_fans);
        tvAge = $(R.id.tv_age);
        tvExplain = $(R.id.tv_explain);
        serviceTagView = $(R.id.service_tag_view);
        tvName = $(R.id.tv_name);

        initRecycler();
    }

    @Override
    public void initData() {
        //TODO 个人信息，个人动态
        mPresenter = new SpacePresenter(context,this);

        mPresenter.userViewZone(1);
        mPresenter.friendPersonalFriendSter(1, Constant.DEFAULT_LIMIT,Constant.DEFAULT_PAGE);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void userViewZoneSuccess(LoginInfoModel data) {
        GlideUtil.LoadCircleImage(context,data.getImgUrl(),ivHead);
        tvName.setText(data.getName());
        tvFans.setText(data.getFansCount()+"粉丝");
        tvAge.setText(data.getBirthday());
        tvExplain.setText(data.getPersonalStatement());

    }

    @Override
    public void userViewZoneFailed(String msg) {

    }

    @Override
    public void friendPersonalFriendSterSuccess(DynamicListModel model) {
        mAdapter.setData(model.getList());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void friendPersonalFriendSterFailed(String msg) {

    }
}
