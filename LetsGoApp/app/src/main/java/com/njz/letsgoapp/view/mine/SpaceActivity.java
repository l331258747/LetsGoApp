package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndLessScrollOnScrollListener;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.find.DynamicNiceContract;
import com.njz.letsgoapp.mvp.find.DynamicNicePresenter;
import com.njz.letsgoapp.mvp.find.FollowContract;
import com.njz.letsgoapp.mvp.find.FollowPresenter;
import com.njz.letsgoapp.mvp.mine.SpaceContract;
import com.njz.letsgoapp.mvp.mine.SpacePresenter;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.StringUtils;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.find.DynamicDetailActivity;
import com.njz.letsgoapp.view.find.ReleaseDynamicActivity;
import com.njz.letsgoapp.widget.EmptyView;
import com.njz.letsgoapp.widget.flowlayout.FlowLayout;
import com.njz.letsgoapp.widget.flowlayout.TagAdapter;
import com.njz.letsgoapp.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function: 个人主页
 */

public class SpaceActivity extends BaseActivity implements SpaceContract.View, View.OnClickListener, FollowContract.View,DynamicNiceContract.View{

    public static final String USER_ID = "USER_ID";

    private RecyclerView recyclerView;
    private DynamicAdapter mAdapter;
    private ImageView ivHead, ivSex;
    private TextView tvFans, tvAge, tvExplain, tvName, tvFollow;
    private TagFlowLayout flowLayout;
    private SpacePresenter mPresenter;
    private FollowPresenter followPresenter;
    private DynamicNicePresenter nicePresenter;

    private NestedScrollView scrollView;

    private int userId;
    LoginInfoModel data;

    public LoadMoreWrapper loadMoreWrapper;
    int page = Constant.DEFAULT_PAGE;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    public EmptyView view_empty;


    @Override
    public void getIntentData() {
        super.getIntentData();
        userId = intent.getIntExtra(USER_ID,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_space;
    }

    @Override
    public void initView() {

        showLeftAndTitle("个人主页");

        view_empty = $(R.id.view_empty);
        ivHead = $(R.id.iv_head);
        ivSex = $(R.id.iv_sex);
        tvFans = $(R.id.tv_fans);
        tvAge = $(R.id.tv_age);
        tvExplain = $(R.id.tv_explain);
        flowLayout = $(R.id.flow_layout);
        tvFollow = $(R.id.tv_follow);
        tvName = $(R.id.tv_name);
        scrollView = $(R.id.scrollView);
        tvFollow.setOnClickListener(this);
        initRecycler();

        tvFans.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRefreshData();
    }

    public void getRefreshData() {
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        getList();
    }

    public void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        getList();
    }

    public void getList(){
        mPresenter.friendPersonalFriendSter(userId, Constant.DEFAULT_LIMIT, page);
    }

    @Override
    public void initData() {
        //TODO 个人信息，个人动态
        mPresenter = new SpacePresenter(context, this);
        followPresenter = new FollowPresenter(context, this);
        nicePresenter = new DynamicNicePresenter(context, this);

        mPresenter.userViewZone(userId);

        if(userId == MySelfInfo.getInstance().getUserId()){
            tvFollow.setVisibility(View.GONE);
        }
    }

    int nicePosition;
    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>(),2);
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        mAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, DynamicDetailActivity.class);
                intent.putExtra(DynamicDetailActivity.FRIENDSTERID,mAdapter.getItem(position).getFriendSterId());
                startActivity(intent);
            }

            @Override
            public void onNiceClick(int position) {
                nicePresenter.friendQueryLikes(mAdapter.getItem(position).isLike(),mAdapter.getItem(position).getFriendSterId());
                nicePosition = position;
            }

            @Override
            public void onHeadClick(int position) {
                Intent intent = new Intent(context, SpaceActivity.class);
                intent.putExtra(SpaceActivity.USER_ID, mAdapter.getItem(position).getUserId());
                startActivity(intent);
            }
        });

        scrollView.setOnScrollChangeListener(new EndLessScrollOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });

    }


    @Override
    public void userViewZoneSuccess(LoginInfoModel data) {
        this.data = data;
        GlideUtil.LoadCircleImage(context, data.getImgUrl(), ivHead);
        tvName.setText(data.getNickname());
        tvAge.setText(TextUtils.isEmpty(data.getBirthday())?"保密":""+DateUtil.getAgeFromBirthTime(data.getBirthday()));
        StringUtils.setHtml(tvFans, String.format(getResources().getString(R.string.mine_fans), MySelfInfo.getInstance().getUserFocus()));
        tvExplain.setText(data.getPersonalStatement());
        ivSex.setImageDrawable(ContextCompat.getDrawable(context, data.getGender() == 2 ? R.mipmap.icon_girl : R.mipmap.icon_boy));
        initFlow(data.getTravelMacroEntitysList());
        setFollow(data.isFocus());
    }

    public void setFollow(boolean isFollow){
        if(isFollow){
            tvFollow.setText("已关注");
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_gray_solid_r5));
            tvFollow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.follow_ok),null,null,null);
        }else{
            tvFollow.setText("加关注");
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_solid_r5));
            tvFollow.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.follow_un),null,null,null);
        }
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
        List<DynamicModel> datas = model.getList();

        if (isLoadType == 1) {
            mAdapter.setData(datas);
        } else {
            mAdapter.addData(datas);
        }

        isLoad = false;
        if (datas.size() >= Constant.DEFAULT_LIMIT) {
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
        } else {
            // 显示加载到底的提示
            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
        }

        if(mAdapter.getDatas().size() == 0){

            if(MySelfInfo.getInstance().getUserId() == userId){
                view_empty.setVisible(true);
                view_empty.setEmptyData(R.mipmap.empty_dynamic_my,"空空如也，说点什么~",null,"去发布");
                view_empty.setBtnClickLisener(new EmptyView.BtnClickLisener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(context, ReleaseDynamicActivity.class));
                    }
                });
            }else{
                view_empty.setVisible(true);
                view_empty.setEmptyData(R.mipmap.empty_dynamic_my,"该主人很懒，什么都没留下");
            }
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void friendPersonalFriendSterFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_follow:
                followPresenter.userFocusOff(data.isFocus(),data.getUserId());
                break;
            case R.id.tv_fans:
                Intent intentFans = new Intent(context,FansListActivity.class);
                intentFans.putExtra(FansListActivity.TITLE, "粉丝列表");
                intentFans.putExtra(FansListActivity.TYPE,0);
                intentFans.putExtra(FansListActivity.USER_ID,data.getUserId());
                startActivity(intentFans);
                break;
        }
    }

    @Override
    public void userFocusOffSuccess(EmptyModel models) {
        data.setFocus(!data.isFocus());
        setFollow(data.isFocus());
    }

    @Override
    public void userFocusOffFailed(String msg) {
        showShortToast(msg);
    }

    @Override
    public void friendQueryLikesSuccess(EmptyModel models) {
        mAdapter.getItem(nicePosition).setLike(!mAdapter.getItem(nicePosition).isLike());
        if(mAdapter.getItem(nicePosition).isLike()){
            mAdapter.getItem(nicePosition).setLikeCount(mAdapter.getItem(nicePosition).getLikeCount() + 1);
        }else{
            mAdapter.getItem(nicePosition).setLikeCount(mAdapter.getItem(nicePosition).getLikeCount() - 1);
        }
        mAdapter.setItemData(nicePosition);

        loadMoreWrapper.notifyItemChanged(nicePosition);
    }

    @Override
    public void friendQueryLikesFailed(String msg) {
        showShortToast(msg);
    }
}
