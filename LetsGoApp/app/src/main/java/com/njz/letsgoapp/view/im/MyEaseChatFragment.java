package com.njz.letsgoapp.view.im;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.njz.letsgoapp.view.im.cache.UserCacheManager;

import java.util.Map;

/**
 * Created by LGQ
 * Time: 2019/1/23
 * Function:
 */

public class MyEaseChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

//    private boolean isRobot;
    private boolean isRobot = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
//        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//            Map<String, RobotUser> robotMap = HxEaseuiHelper.getInstance().getRobotList();
//            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
//                isRobot = true;
//            }
//        }
        super.setUpView();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
        //设置要发送扩展消息用户昵称
//        message.setAttribute(Constant.IM_NICKNAME, MySelfInfo.getInstance().getUserNickname());
//        message.setAttribute(Constant.IM_HEADIMG, MySelfInfo.getInstance().getUserImgUrl());

        UserCacheManager.setMsgExt(message);
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}
