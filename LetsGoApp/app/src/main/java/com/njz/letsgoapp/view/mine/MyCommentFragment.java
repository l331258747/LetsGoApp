package com.njz.letsgoapp.view.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.mine.MyCommentAdapter;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.mine.MyCommentContract;
import com.njz.letsgoapp.mvp.mine.MyCommentPresenter;
import com.njz.letsgoapp.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentFragment extends BaseFragment implements MyCommentContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyCommentAdapter mAdapter;

    private int type = 1;

    MyCommentPresenter mPresenter;
    private boolean isViewCreated;
    boolean isLoad = false;

    public EmptyView view_empty;

    public static Fragment newInstance(int type) {
        MyCommentFragment fragment = new MyCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("TYPE");
        }
        isViewCreated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);//比oncreate先执行
        if (isVisibleToUser && isViewCreated && !isLoad) {
            getRefreshData();
        }
    }

    public void getRefreshData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        mPresenter.friendMyDiscuss(type);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_comment;
    }

    @Override
    public void initView() {

        view_empty = $(R.id.view_empty);

        initRecycler();
        initSwipeLayout();
    }

    //初始化recyclerview
    private void initRecycler() {
        recyclerView = $(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MyCommentAdapter(activity, new ArrayList<MyCommentModel>(), type);
        recyclerView.setAdapter(mAdapter);
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
    public void initData() {
        mPresenter = new MyCommentPresenter(context, this);
        if(getUserVisibleHint()){
            getRefreshData();
        }
    }


    @Override
    public void friendMyDiscussSuccess(List<MyCommentModel> data) {
        List<MyCommentModel> datas = data;
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.setData(datas);

        if(data.size() == 0){
            if(type == 1){
                view_empty.setVisible(true);
                view_empty.setEmptyData(R.mipmap.empty_comment_tome,"空空如也，请勤劳发帖！");
            }else {
                view_empty.setVisible(true);
                view_empty.setEmptyData(R.mipmap.empty_comment_meto,"与人互动，心自徜徉");
            }

        }else{
            view_empty.setVisible(false);
        }
    }

    @Override
    public void friendMyDiscussFailed(String msg) {
        showShortToast(msg);
        isLoad = false;
        swipeRefreshLayout.setRefreshing(false);

        if(msg.startsWith("-")){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyView.BtnClickLisener() {
                @Override
                public void onClick() {
                    getRefreshData();
                }
            });
        }
    }
}
