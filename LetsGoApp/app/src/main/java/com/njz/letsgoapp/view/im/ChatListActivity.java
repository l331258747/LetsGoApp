package com.njz.letsgoapp.view.im;

import android.content.Intent;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;

/**
 * Created by LGQ
 * Time: 2019/1/15
 * Function:
 */

public class ChatListActivity extends BaseActivity {

    private static EMMessageListener emMessageListener;
    private EaseConversationListFragment conversationFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void initView() {
        hideTitleLayout();

        conversationFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.chat_list,conversationFragment).commit();
        conversationFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId()));
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }

}
