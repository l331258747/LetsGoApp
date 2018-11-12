package com.njz.letsgoapp.view.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.base.EndlessRecyclerOnScrollListener;
import com.njz.letsgoapp.adapter.base.LoadMoreWrapper;
import com.njz.letsgoapp.adapter.home.DynamicAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.find.DynamicNiceContract;
import com.njz.letsgoapp.mvp.find.DynamicNicePresenter;
import com.njz.letsgoapp.mvp.find.FindContract;
import com.njz.letsgoapp.mvp.find.FindPresenter;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.mine.SpaceActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyClickLisener;
import com.njz.letsgoapp.widget.emptyView.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/4
 * Function:
 */

public class DynamicFragment extends BaseFragment implements FindContract.View, View.OnClickListener,DynamicNiceContract.View {

    public static final int DYNAMIC_ALL = 0;
    public static final int DYNAMIC_FOLLOW = 1;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DynamicAdapter mAdapter;
    private LoadMoreWrapper loadMoreWrapper;

    private int dynamicTYpe;
    private int nicePosition;

    private FindPresenter mPresenter;
    private DynamicNicePresenter nicePresenter;
    int page;
    int isLoadType = 1;//1下拉刷新，2上拉加载
    boolean isLoad = false;//是否在加载，重复加载问题
    private String city = MySelfInfo.getInstance().getDefaultCity();
    private boolean isViewCreated;
    private boolean hidden;
    private String search;

    public EmptyView view_empty;

    public static Fragment newInstance(int type) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DYNAMIC_TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dynamicTYpe = bundle.getInt("DYNAMIC_TYPE");
        }
        isViewCreated = true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find_dynamic;
    }

    @Override
    public void initView() {
        view_empty = $(R.id.view_empty);

        initRecycler();
        initSwipeLayout();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);//比oncreate先执行
        if (isVisibleToUser && isViewCreated && !isLoad) {
            if(dynamicTYpe == DYNAMIC_FOLLOW) {
                if(setLogin()){
                    getRefreshData();
                }
            }else{
                getRefreshData();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(hidden) return;
        if(getUserVisibleHint()){
            if(dynamicTYpe == DYNAMIC_FOLLOW) {
                if(setLogin()){
                    getRefreshData();
                }
            }else{
                getRefreshData();
            }
        }
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
        if(!hidden){
            if(getUserVisibleHint()){
                if(dynamicTYpe == DYNAMIC_FOLLOW) {
                    if(setLogin()){
                        getRefreshData();
                    }
                }else{
                    getRefreshData();
                }
            }
        }
    }

    public boolean setLogin(){
        boolean isLogin;
        if (dynamicTYpe == DYNAMIC_FOLLOW && !MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_comment_tome, "查看关注请先登录", null,"登录");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    startActivity(new Intent(context, LoginActivity.class));
                }
            });
            isLogin = false;
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            view_empty.setVisible(false);
            isLogin = true;
        }
        return isLogin;
    }

    @Override
    public void initData() {
        mPresenter = new FindPresenter(context, this);
        nicePresenter = new DynamicNicePresenter(context,this);
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DynamicAdapter(activity, new ArrayList<DynamicModel>() , 2);
        loadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//itemChanged 闪烁问题

        page = Constant.DEFAULT_PAGE;

        mAdapter.setOnItemClickListener(new DynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, DynamicDetailActivity.class);
                intent.putExtra(DynamicDetailActivity.FRIENDSTERID, mAdapter.getItem(position).getFriendSterId());
                startActivity(intent);
            }

            @Override
            public void onNiceClick(int position) {
                nicePresenter.friendQueryLikes(mAdapter.getItem(position).isLike(), mAdapter.getItem(position).getFriendSterId());
                nicePosition = position;
            }

            @Override
            public void onHeadClick(int position) {
                Intent intent = new Intent(context, SpaceActivity.class);
                intent.putExtra(SpaceActivity.USER_ID, mAdapter.getItem(position).getUserId());
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isLoad || loadMoreWrapper.getLoadState() == LoadMoreWrapper.LOADING_END) return;
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                getMoreData();
            }
        });


    }

    public void setCityChange(String city) {
        this.city = city;
        if(dynamicTYpe == DYNAMIC_ALL && getUserVisibleHint()) {
            getRefreshData();
        }
    }

    public void setSearch(String str){
        if(dynamicTYpe == DYNAMIC_ALL && getUserVisibleHint()) {
            search = str;
            getRefreshData();
        }else {
            showShortToast("请在全部进行搜索!");
        }
    }

    private void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        page = Constant.DEFAULT_PAGE;
        isLoadType = 1;
        if(dynamicTYpe == DYNAMIC_FOLLOW){
            mPresenter.friendFriendSter(Constant.DEFAULT_LIMIT, page);
        }else{
            mPresenter.friendFindAll(city, Constant.DEFAULT_LIMIT, page,search);
        }
    }

    private void getMoreData() {
        isLoad = true;
        page = page + 1;
        isLoadType = 2;
        if(dynamicTYpe == DYNAMIC_FOLLOW){
            mPresenter.friendFriendSter(Constant.DEFAULT_LIMIT, page);
        }else{
            mPresenter.friendFindAll(city, Constant.DEFAULT_LIMIT, page,search);
        }
    }


    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getRefreshData();
            }
        });
    }

    @Override
    public void friendFindAllSuccess(DynamicListModel models) {
        List<DynamicModel> datas = models.getList();

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
        swipeRefreshLayout.setRefreshing(false);

        if(mAdapter.getDatas().size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_follow,"空空如也~");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void friendFindAllFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);

        if(msg.startsWith("-")){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    getRefreshData();
                }
            });
        }
    }

    @Override
    public void friendFriendSterSuccess(List<DynamicModel> models) {
        List<DynamicModel> datas = models;

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
        swipeRefreshLayout.setRefreshing(false);

        if(mAdapter.getDatas().size() == 0){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_follow,"你还没有关注任何人哦","你不主动，我们怎么会有故事");
        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void friendFriendSterFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);

        if(msg.startsWith("-")){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    getRefreshData();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void friendQueryLikesSuccess(EmptyModel models) {

        mAdapter.getItem(nicePosition).setLike(!mAdapter.getItem(nicePosition).isLike());
        if(mAdapter.getItem(nicePosition).isLike()){
            mAdapter.getItem(nicePosition).setLikeCount(mAdapter.getItem(nicePosition).getLikeCount() + 1);
        }else{
            mAdapter.getItem(nicePosition).setLikeCount(mAdapter.getItem(nicePosition).getLikeCount() - 1);
        }
        loadMoreWrapper.notifyItemChanged(nicePosition);

    }

    @Override
    public void friendQueryLikesFailed(String msg) {
        showShortToast(msg);
    }
}
