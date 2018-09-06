package com.njz.letsgoapp.view.mine;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.mine.SpaceContract;
import com.njz.letsgoapp.mvp.mine.SpacePresenter;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class SpaceActivity extends BaseActivity implements SpaceContract.View, View.OnClickListener {

    private RecyclerView recyclerView;
    private DynamicAdapter mAdapter;
    private ImageView ivHead, ivSex;
    private TextView tvFans, tvAge, tvExplain, tvName, tvFollow;
    private TagFlowLayout flowLayout;
    private SpacePresenter mPresenter;

    private int userId;
    List<DynamicModel> datas;

    @Override
    public void getIntentData() {
        super.getIntentData();
        userId = intent.getIntExtra("userId",0);
    }

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
        flowLayout = $(R.id.flow_layout);
        tvFollow = $(R.id.tv_follow);
        tvName = $(R.id.tv_name);

        tvFollow.setOnClickListener(this);

        initRecycler();

    }

    @Override
    public void initData() {
        //TODO 个人信息，个人动态
        mPresenter = new SpacePresenter(context, this);
        mPresenter.userViewZone(userId);
        mPresenter.friendPersonalFriendSter(userId, Constant.DEFAULT_LIMIT, Constant.DEFAULT_PAGE);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>(),1);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void userViewZoneSuccess(LoginInfoModel data) {
        GlideUtil.LoadCircleImage(context, data.getImgUrl(), ivHead);
        tvName.setText(data.getNickname());
        StringUtils.setHtml(tvFans, String.format(getResources().getString(R.string.mine_fans), MySelfInfo.getInstance().getUserFocus()));
        tvAge.setText(data.getBirthday());
        tvExplain.setText(data.getPersonalStatement());
        ivSex.setImageDrawable(ContextCompat.getDrawable(context, data.getGender() == 0 ? R.mipmap.icon_girl : R.mipmap.icon_boy));
        initFlow(data.getTravelMacroEntitysList());
    }

    public void initFlow(final List<LabelItemModel> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        TagAdapter adapter1 = new TagAdapter<LabelItemModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, LabelItemModel s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_label, flowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        };
        flowLayout.setAdapter(adapter1);
    }

    @Override
    public void userViewZoneFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void friendPersonalFriendSterSuccess(DynamicListModel model) {
        datas = model.getList();
        mAdapter.setData(model.getList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void friendPersonalFriendSterFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_follow:
                tvFollow.setText("已关注");
                tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_gray_solid_r5));
                tvFollow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.follow_ok),null,null,null);
                break;
        }
    }

}
