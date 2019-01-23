package com.njz.letsgoapp.view.homeFragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.ActivityCollect;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.mvp.notify.NotifyMainContract;
import com.njz.letsgoapp.mvp.notify.NotifyMainPresenter;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.util.rxbus.RxBus2;
import com.njz.letsgoapp.util.rxbus.busEvent.NotifyEvent;
import com.njz.letsgoapp.view.im.ChatActivity;
import com.njz.letsgoapp.view.im.ChatHelp;
import com.njz.letsgoapp.view.login.LoginActivity;
import com.njz.letsgoapp.view.notify.InteractionMsgActivity;
import com.njz.letsgoapp.view.notify.SystemMsgActivity;
import com.njz.letsgoapp.widget.emptyView.EmptyClickLisener;
import com.njz.letsgoapp.widget.emptyView.EmptyView;
import com.njz.letsgoapp.widget.NotifyItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/7/23
 * Function:
 */
public class NotifyFragment extends BaseFragment implements View.OnClickListener, NotifyMainContract.View {

    private boolean hidden;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoad;

    private NotifyMainPresenter mPresenter;

    NotifyItemView view_notify_interaction, view_notify_message;

    public EmptyView view_empty;
    public boolean isGoLogin;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notify;
    }

    @Override
    public void initView() {

        conversationListView = $(R.id.list);
        view_empty = $(R.id.view_empty);
        view_notify_interaction = $(R.id.view_notify_interaction);
        view_notify_message = $(R.id.view_notify_message);
        view_notify_interaction.setOnClickListener(this);
        view_notify_message.setOnClickListener(this);

        initSwipeLayout();
    }

    public boolean setLogin() {
        boolean isLogin;
        if (!MySelfInfo.getInstance().isLogin()) {
            swipeRefreshLayout.setVisibility(View.GONE);
            view_empty.setVisible(true);
            view_empty.setEmptyData(R.mipmap.empty_comment_tome, "查看消息请先登录", null, "登录");
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
        if (!hidden) {
            if (setLogin()) {
                getData();
                if (!isConflict) {
                    refresh();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hidden) return;
        if (setLogin() && isGoLogin) {
            getData();
            setUpView();
        } else if (setLogin() && !isGoLogin) {
            refresh();
        }

        isGoLogin = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    @Override
    public void initData() {
        mPresenter = new NotifyMainPresenter(context, this);
        if (setLogin()) {
            getData();
            setUpView();
        }
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    public void getData() {
        swipeRefreshLayout.setRefreshing(true);
        isLoad = true;
        mPresenter.msgPushGetSendMsgList();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.view_notify_interaction:
                setNotifyItemEmpty(view_notify_message);
                intent = new Intent(context, InteractionMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.view_notify_message:
                setNotifyItemEmpty(view_notify_interaction);
                intent = new Intent(context, SystemMsgActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void msgPushGetSendMsgListSuccess(List<NotifyMainModel> data) {
        swipeRefreshLayout.setRefreshing(false);
        isLoad = false;

        boolean hasItem = false;
        for (NotifyMainModel model : data) {
            if (TextUtils.equals(model.getMsgBroad(), Constant.NOTIFY_TYPE_SYSTEM_MSG)) {
                setNofityItem(view_notify_message, model);
                hasItem = true;
            }
        }
        if (!hasItem) {
            setNotifyItemEmpty(view_notify_message);
        }

        hasItem = false;
        for (NotifyMainModel model : data) {
            if (TextUtils.equals(model.getMsgBroad(), Constant.NOTIFY_TYPE_INTERACTION)) {
                setNofityItem(view_notify_interaction, model);
                hasItem = true;

            }
        }
        if (!hasItem) {
            setNotifyItemEmpty(view_notify_interaction);
        }

        view_empty.setVisible(false);

        refresh();
    }

    public void setNotifyItemEmpty(NotifyItemView item) {
        item.setContent("暂无新消息");
        item.getViewNum().setVisibility(View.GONE);
        item.setTime("");
    }

    public void setNofityItem(NotifyItemView item, NotifyMainModel model) {
        if (model.getContent() != null) {
            item.setContent(model.getContent().getAlert());
        }
        item.setTime(model.getStartTimeTwo());
        if (model.getUnReadNum() < 1) {
            item.getViewNum().setVisibility(View.GONE);
        } else {
            item.getViewNum().setVisibility(View.VISIBLE);
            item.setNum(model.getUnReadNum());
        }
    }

    @Override
    public void msgPushGetSendMsgListFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showShortToast(msg);
        isLoad = false;

        if (msg.startsWith("-")) {
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


    //-----------------------------------------环信

    boolean isMessageListInited;
    private ChatHelp chatHelp;
    protected EaseConversationList conversationListView;
    protected boolean isConflict;

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    LogUtil.e("im 0");
                    break;
                case 1:
                    LogUtil.e("im 1");
                    break;
                case ChatHelp.MSG_REFRESH: {
                    chatHelp.refresh();
                    break;
                }
                default:
                    break;
            }
        }
    };

    protected void setUpView() {
        chatHelp = new ChatHelp(conversationListView);

        chatHelp.setConversationListItemClickListener(
                new ChatHelp.EaseConversationListItemClickListener() {
                    @Override
                    public void onListItemClicked(EMConversation conversation) {
                        startActivity(new Intent(context, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
                    }
                }, new EMConnectionListener() {
                    @Override
                    public void onDisconnected(int error) {
                        if (chatHelp.isError(error)) {
                            isConflict = true;
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }

                    @Override
                    public void onConnected() {
                        handler.sendEmptyMessage(1);
                    }
                });
        isMessageListInited = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatHelp.removeConnectionListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    public void refresh() {
        if (!handler.hasMessages(ChatHelp.MSG_REFRESH)) {
            handler.sendEmptyMessage(ChatHelp.MSG_REFRESH);
        }
    }

    //--------------提醒
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            if (!hidden && !isConflict && setLogin()) {
                chatHelp.refresh();
            }else if(setLogin()) {
                RxBus2.getInstance().post(new NotifyEvent(true));
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
        }


        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };


}