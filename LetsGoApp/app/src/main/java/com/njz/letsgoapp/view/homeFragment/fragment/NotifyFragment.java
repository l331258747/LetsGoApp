package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.mvp.notify.NotifyMainContract;
import com.njz.letsgoapp.mvp.notify.NotifyMainPresenter;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.notify.InteractionMsgActivity;
import com.njz.letsgoapp.view.notify.SystemMsgActivity;
import com.njz.letsgoapp.widget.NotifyItemView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */
public class NotifyFragment extends BaseFragment implements View.OnClickListener,NotifyMainContract.View {

    private TextView tvLogin;
    private boolean hidden;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoad;

    private NotifyMainPresenter mPresenter;

    NotifyItemView view_notify_interaction,view_notify_message;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_notify;
    }

    @Override
    public void initView() {

        view_notify_interaction = $(R.id.view_notify_interaction);
        view_notify_message = $(R.id.view_notify_message);
        tvLogin = $(R.id.tv_login);
        tvLogin.setOnClickListener(this);
        view_notify_interaction.setOnClickListener(this);
        view_notify_message.setOnClickListener(this);

        initSwipeLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(hidden) return;
        if(setLogin()){
            getData();
        }
    }

    public boolean setLogin(){
        boolean isLogin;
        if (!MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            isLogin = false;
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            isLogin = true;
        }
        return isLogin;
    }

    //初始化SwipeLayout
    private void initSwipeLayout() {
        swipeRefreshLayout = $(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoad) return;
                getData();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if(!hidden){
            if(setLogin()){
                getData();
            }
        }
    }


    @Override
    public void initData() {
        mPresenter = new NotifyMainPresenter(context,this);
    }

    public void getData(){
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        mPresenter.msgPushGetSendMsgList();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.view_notify_interaction:
                intent = new Intent(context,InteractionMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.view_notify_message:
                intent = new Intent(context,SystemMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                startActivity(new Intent(context, LoginActivity.class));
                break;
        }
    }

    @Override
    public void msgPushGetSendMsgListSuccess(List<EmptyModel> data) {
        swipeRefreshLayout.setRefreshing(false);
        isLoad = false;
    }

    @Override
    public void msgPushGetSendMsgListFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showShortToast(msg);
        isLoad = false;
    }
}