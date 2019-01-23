package com.njz.letsgoapp.view.im;

import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by LGQ
 * Time: 2019/1/15
 * Function:
 */

public class ChatHelp {
    public final static int MSG_REFRESH = 2;
    public EaseConversationListItemClickListener listItemClickListener;
    public EMConnectionListener connectionListener;

    public List<EMConversation> conversationList = new ArrayList<EMConversation>();
    public EaseConversationList conversationListView;

    public ChatHelp(EaseConversationList view) {

        this.conversationList = new ArrayList<EMConversation>();
        this.conversationListView = view;

        conversationList.addAll(loadConversationList());
        conversationListView.init(conversationList);

    }

    public loadCallBack callBack;
    public void setLoadCallback(loadCallBack callBack){
        this.callBack = callBack;
    }
    public interface loadCallBack{
        void loadCallback(List<EMConversation> list);
    }

    public void setConversationListItemClickListener(EaseConversationListItemClickListener listener1,
                                                     EMConnectionListener listener2) {
        this.listItemClickListener = listener1;
        this.connectionListener = listener2;

        if (listItemClickListener != null) {
            conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation conversation = conversationListView.getItem(position);
                    listItemClickListener.onListItemClicked(conversation);
                }
            });
        }

        if(connectionListener != null){
            EMClient.getInstance().addConnectionListener(connectionListener);
        }
    }

    public List<EMConversation> loadConversationList() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }

        if(callBack != null)
            callBack.loadCallback(list);

        return list;
    }

    public void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    public boolean isError(int error){
        if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
            return true;
        }
        return false;
    }

    public void removeConnectionListener(){
        if(connectionListener != null)
            EMClient.getInstance().removeConnectionListener(connectionListener);
    }


    public interface EaseConversationListItemClickListener {
        void onListItemClicked(EMConversation conversation);
    }

    public void refresh() {
        if(conversationList == null) return;
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        conversationListView.refresh();
    }
}
