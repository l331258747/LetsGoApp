package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.notify.NotifyMainContract;
import com.njz.letsgoapp.mvp.notify.NotifyMainPresenter;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.notify.InteractionMsgActivity;
import com.njz.letsgoapp.view.notify.SystemMsgActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyClickLisener;
import com.njz.letsgoapp.widget.emptyView.EmptyView;
import com.njz.letsgoapp.widget.NotifyItemView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */
public class NotifyFragment extends BaseFragment implements View.OnClickListener,NotifyMainContract.View {

    private boolean hidden;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoad;

    private NotifyMainPresenter mPresenter;

    NotifyItemView view_notify_interaction,view_notify_message;

    public EmptyView view_empty;
    public boolean isGoLogin;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notify;
    }

    @Override
    public void initView() {

        view_empty = $(R.id.view_empty);
        view_notify_interaction = $(R.id.view_notify_interaction);
        view_notify_message = $(R.id.view_notify_message);
        view_notify_interaction.setOnClickListener(this);
        view_notify_message.setOnClickListener(this);

        initSwipeLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(hidden) return;
        if(setLogin() && isGoLogin){
            getData();
        }
        isGoLogin = false;
    }

    public boolean setLogin(){
        boolean isLogin;
        if (!MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_comment_tome, "查看消息请先登录", null,"登录");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    isGoLogin = true;
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

        if(setLogin()){
            getData();
        }
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
                setNotifyItemEmpty(view_notify_message);
                intent = new Intent(context,InteractionMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.view_notify_message:
                setNotifyItemEmpty(view_notify_interaction);
                intent = new Intent(context,SystemMsgActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void msgPushGetSendMsgListSuccess(List<NotifyMainModel> data) {
        swipeRefreshLayout.setRefreshing(false);
        isLoad = false;

        boolean hasItem = false;
        for (NotifyMainModel model : data){
            if(TextUtils.equals(model.getMsgBroad(),Constant.NOTIFY_TYPE_SYSTEM_MSG)){
                setNofityItem(view_notify_message,model);
                hasItem = true;
            }
        }
        if(!hasItem){
            setNotifyItemEmpty(view_notify_message);
        }

        hasItem = false;
        for (NotifyMainModel model : data){
            if(TextUtils.equals(model.getMsgBroad(),Constant.NOTIFY_TYPE_INTERACTION)){
                setNofityItem(view_notify_interaction,model);
                hasItem = true;

            }
        }
        if(!hasItem){
            setNotifyItemEmpty(view_notify_interaction);
        }

        view_empty.setVisible(false);

    }

    public void setNotifyItemEmpty(NotifyItemView item){
        item.setContent("暂无新消息");
        item.getViewNum().setVisibility(View.GONE);
        item.setTime("");
    }

    public void setNofityItem(NotifyItemView item,NotifyMainModel model){
        if(model.getContent() != null){
            item.setContent(model.getContent().getAlert());
        }
        item.setTime(model.getStartTimeTwo());
        if(model.getUnReadNum() < 1){
            item.getViewNum().setVisibility(View.GONE);
        }else{
            item.getViewNum().setVisibility(View.VISIBLE);
            item.setNum(model.getUnReadNum());
        }
    }

    @Override
    public void msgPushGetSendMsgListFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showShortToast(msg);
        isLoad = false;

        if(msg.startsWith("-")){
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_network, "网络竟然崩溃了", "别紧张，试试看刷新页面~", "点击刷新");
            view_empty.setBtnClickLisener(new EmptyClickLisener() {
                @Override
                public void onClick() {
                    getData();
                }
            });
        }
    }
}