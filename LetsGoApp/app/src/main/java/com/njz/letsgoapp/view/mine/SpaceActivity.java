package com.njz.letsgoapp.view.mine;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndLessScrollOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.SpaceDynamicAdapter;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.mine.LabelItemModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.ShareDialog;
import com.njz.letsgoapp.mvp.find.FollowContract;
import com.njz.letsgoapp.mvp.find.FollowPresenter;
import com.njz.letsgoapp.mvp.mine.SpaceContract;
import com.njz.letsgoapp.mvp.mine.SpacePresenter;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.DateUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.find.DynamicDetailActivity;
import com.njz.letsgoapp.view.find.ReleaseDynamicActivity;
import com.njz.letsgoapp.view.find.SpaceDynamicDetailActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyClickLisener;
import com.njz.letsgoapp.widget.emptyView.EmptyView2;
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

public class SpaceActivity extends BaseActivity implements SpaceContract.View, View.OnClickListener, FollowContract.View{

    public static final String USER_ID = "USER_ID";

    private RecyclerView recyclerView;
    private SpaceDynamicAdapter mAdapter;
    private ImageView ivHead, ivSex;
    private TextView tvFans, tvAge, tvExplain, tvName, tvFollow,tvModify,tv_title_title;
    private TagFlowLayout flowLayout;
    private SpacePresenter mPresenter;
    private FollowPresenter followPresenter;
    private RelativeLayout rl_title_parent;

    private NestedScrollView scrollView;

    private int userId;
    LoginInfoModel data;

    public LoadMoreWrapper loadMoreWrapper;
    int page = Constant.DEFAULT_PAGE;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题

    public EmptyView2 view_empty;
    public ImageView iv_back,iv_share;
    private View view_title_line;


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

        hideTitleLayout();

        view_title_line = $(R.id.view_title_line);
        rl_title_parent = $(R.id.rl_title_parent);
        tv_title_title = $(R.id.tv_title_title);
        iv_back = $(R.id.iv_back);
        iv_share = $(R.id.iv_share);
        view_empty = $(R.id.view_empty);
        ivHead = $(R.id.iv_head);
        tvModify = $(R.id.tv_modify);
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
        tvModify.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.userViewZone(userId);
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

        if(userId == MySelfInfo.getInstance().getUserId()){
            tvFollow.setVisibility(View.GONE);
            tvModify.setVisibility(View.VISIBLE);
        }else{
            tvFollow.setVisibility(View.VISIBLE);
            tvModify.setVisibility(View.GONE);
        }

        getRefreshData();

    }

    int nicePosition;
    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapter = new SpaceDynamicAdapter(activity, new ArrayList<DynamicModel>());
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        recyclerView.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        mAdapter.setOnItemClickListener(new SpaceDynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, SpaceDynamicDetailActivity.class);
                intent.putExtra(DynamicDetailActivity.FRIENDSTERID,mAdapter.getItem(position).getFriendSterId());
                startActivity(intent);
            }

        });

        final int mDisplayHeight = AppUtils.dip2px(150 - 42);
        scrollView.setOnScrollChangeListener(new EndLessScrollOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }

            @Override
            public void onScrollChange(int scrollY) {
                if (scrollY > mDisplayHeight) {
                    rl_title_parent.setBackgroundResource(R.color.white);
                    tv_title_title.setTextColor(ContextCompat.getColor(context,R.color.color_text));
                    iv_back.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_back));
                    iv_share.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_share));
                    view_title_line.setVisibility(View.VISIBLE);

                } else {
                    rl_title_parent.setBackgroundResource(R.color.transparent);
                    tv_title_title.setTextColor(ContextCompat.getColor(context,R.color.white));
                    iv_back.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_back_white));
                    iv_share.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_share_white));
                    view_title_line.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public void userViewZoneSuccess(LoginInfoModel data) {
        this.data = data;
        GlideUtil.LoadCircleImage(context, data.getImgUrl(), ivHead);
        tvName.setText(data.getNickname());
        tvAge.setText(TextUtils.isEmpty(data.getBirthday())?"保密":""+DateUtil.getAgeFromBirthTime(data.getBirthday()));
        tvFans.setText(data.getFansCount()+"");
        tvExplain.setText(TextUtils.isEmpty(data.getPersonalStatement())?"无":data.getPersonalStatement());
        ivSex.setImageDrawable(ContextCompat.getDrawable(context, data.getGender() == 2 ? R.mipmap.icon_girl : R.mipmap.icon_boy));
        initFlow(data.getLabelList());
        setFollow(data.isFocus());

        if(data.getUserId() == MySelfInfo.getInstance().getUserId()){
            iv_share.setVisibility(View.GONE);
        }else{
            iv_share.setVisibility(View.VISIBLE);
        }
    }

    public void setFollow(boolean isFollow){
        if(isFollow){
            tvFollow.setText(getResources().getString(R.string.follow_in));
            tvFollow.setTextColor(ContextCompat.getColor(context,R.color.white));
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_solid_r5));
        }else{
            tvFollow.setText(getResources().getString(R.string.follow_un));
            tvFollow.setTextColor(ContextCompat.getColor(context,R.color.color_theme));
            tvFollow.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_hollow_r5));
        }
    }

    public void initFlow(final List<LabelItemModel> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(activity);
        TagAdapter adapter1 = new TagAdapter<LabelItemModel>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, LabelItemModel s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_space, flowLayout, false);
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
                view_empty.setBtnClickLisener(new EmptyClickLisener() {
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
//            recyclerView.addItemDecoration(new TitleItemDecoration(context,mAdapter.getDatas(),2));
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
            case R.id.tv_modify:
                startActivity(new Intent(context,MyInfoActivity.class));
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_share:
                if(data == null) return;
                ShareDialog dialog = new ShareDialog(activity, "", "", "", "");
                dialog.setReportData(data.getUserId(), ShareDialog.REPORT_USER);
                dialog.setType(ShareDialog.TYPE_REPORT);
                dialog.show();
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

}
